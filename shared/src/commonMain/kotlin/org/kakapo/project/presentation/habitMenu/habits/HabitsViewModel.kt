package org.kakapo.project.presentation.habitMenu.habits

import androidx.lifecycle.viewModelScope
import com.kakapo.common.asResult
import com.kakapo.common.subscribe
import com.kakapo.common.util.todayAtMidnight
import com.kakapo.data.repository.base.habit.HabitCheckRepository
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.model.habit.HabitItemModel
import com.kakapo.model.habit.HabitType
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("HabitsViewModelKt")
class HabitsViewModel(
    private var habitRepository: HabitRepository,
    private var habitCheckRepository: HabitCheckRepository
): BaseViewModel<HabitsState, HabitsEffect, HabitsEvent>(HabitsState()) {

    override fun handleEvent(event: HabitsEvent) {
        when(event) {
            HabitsEvent.TapToAddHabit -> emit(HabitsEffect.TapToAddHabit)
            HabitsEvent.NavigateBack -> emit(HabitsEffect.NavigateBack)
            is HabitsEvent.TappedHabit -> onTappedHabit(event.id, event.type)
            is HabitsEvent.CheckedGoodHabit -> onCheckedGoodHabit(event.id, event.isChecked)
        }
    }

    fun initData() {
        loadHabitsToday()
    }

    private fun loadHabitsToday() = viewModelScope.launch {
        val onSuccess: (List<HabitItemModel>) -> Unit = { habits ->
            _uiState.update { it.copy(habits = habits, loading = false) }
        }

        habitRepository.loadHabits(todayAtMidnight).asResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun onCheckedGoodHabit(habitId: Long, isChecked: Boolean) {
        if (isChecked) checkedGoodHabitBy(habitId)
        else uncheckedGoodHabitBy(habitId)
    }

    private fun checkedGoodHabitBy(habitId: Long) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            loadHabitsToday()
        }

        habitCheckRepository.saveTodayCheckBy(habitId, todayAtMidnight)
            .onSuccess(onSuccess)
            .onFailure(::handleError)
    }

    private fun uncheckedGoodHabitBy(habitId: Long) = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            loadHabitsToday()
        }

        habitCheckRepository.deleteTodayCheckBy(habitId, todayAtMidnight)
            .onSuccess(onSuccess)
            .onFailure(::handleError)
    }

    private fun onTappedHabit(habitId: Long, type: HabitType) {
        when (type) {
            HabitType.GOOD -> emit(HabitsEffect.NavigateToGoodHabit(habitId))
            HabitType.BAD -> emit(HabitsEffect.NavigateToBadHabit(habitId))
        }
    }

    private fun handleError(throwable: Throwable?) {
        val errorMessage = throwable?.message ?: "An unknown error occurred"
        emit(HabitsEffect.ShowError(errorMessage))
    }
}