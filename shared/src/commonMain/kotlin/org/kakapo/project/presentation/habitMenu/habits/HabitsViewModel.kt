package org.kakapo.project.presentation.habitMenu.habits

import androidx.lifecycle.viewModelScope
import com.kakapo.common.asResult
import com.kakapo.common.subscribe
import com.kakapo.data.repository.base.HabitRepository
import com.kakapo.model.habit.HabitModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("HabitsViewModelKt")
class HabitsViewModel(
    private var habitRepository: HabitRepository
): BaseViewModel<HabitsState, HabitsEffect, HabitsEvent>(HabitsState()) {

    override fun handleEvent(event: HabitsEvent) {
        when(event) {
            HabitsEvent.TapToAddHabit -> emit(HabitsEffect.TapToAddHabit)
            HabitsEvent.NavigateBack -> emit(HabitsEffect.NavigateBack)
        }
    }

    fun initData() {
        loadHabitsToday()
    }

    private fun loadHabitsToday() = viewModelScope.launch {
        val onSuccess: (List<HabitModel>) -> Unit = { habits ->
            _uiState.update { it.copy(habits = habits, loading = false) }
        }

        habitRepository.loadHabitsToday().asResult().subscribe(
            onSuccess = onSuccess,
            onError = ::handleError
        )
    }

    private fun handleError(throwable: Throwable?) {
        val errorMessage = throwable?.message ?: "An unknown error occurred"
        emit(HabitsEffect.ShowError(errorMessage))
    }
}