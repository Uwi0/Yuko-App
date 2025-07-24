package org.kakapo.project.presentation.habitMenu.goodHabit

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.domain.model.goodHabitParamFactory
import com.kakapo.domain.useCase.base.GoodHabitDetailUseCase
import com.kakapo.model.habit.GoodHabitModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import org.kakapo.project.util.date.calendar.CalendarStore
import org.kakapo.project.util.date.horizontalCalendar.HorizontalCalendarStore
import kotlin.native.ObjCName

@ObjCName("GoodHabitViewModelKt")
class GoodHabitViewModel(
    private val habitRepository: HabitRepository,
    private val goodHabitDetail: GoodHabitDetailUseCase
) : BaseViewModel<GoodHabitState, GoodHabitEffect, GoodHabitEvent>(GoodHabitState()) {

    private var habitId: Long = 0L
    private val weeksStore = HorizontalCalendarStore()
    private val monthsStore = CalendarStore()

    override fun handleEvent(event: GoodHabitEvent) {
        when (event) {
            is GoodHabitEvent.ChangeCompletionMode -> _uiState.update { it.updateNext(event.mode) }
            is GoodHabitEvent.UpdateWeek -> weeksStore.update(event.index)
            is GoodHabitEvent.UpdateMonth -> monthsStore.update(event.index)
            GoodHabitEvent.DeleteHabit -> deleteHabit()
            GoodHabitEvent.NavigateBack -> emit(GoodHabitEffect.NavigateBack)
        }
    }

    fun initData(habitId: Long) {
        this.habitId = habitId
        loadGoodHabitBy(habitId)
        observeStore()
        weeksStore.initData()
        monthsStore.initData()
    }

    private fun observeStore() {
        weeksStore.onCalendarUpdate = { weeks, date ->
            _uiState.update { it.copy(allWeeks = weeks, currentDate = date) }
        }
        monthsStore.onCalendarUpdate = { months, date ->
            _uiState.update { it.copy(allMonths = months, currentDate = date) }
        }
    }

    private fun loadGoodHabitBy(habitId: Long) = viewModelScope.launch {
        val param = goodHabitParamFactory(habitId)
        val onSuccess: (GoodHabitModel) -> Unit = { goodHabit ->
            _uiState.update { it.copy(goodHabit = goodHabit) }
        }

        goodHabitDetail.execute(param)
            .onSuccess(onSuccess)
            .onFailure(::handleError)
    }

    private fun deleteHabit() = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            emit(GoodHabitEffect.NavigateBack)
        }

        habitRepository.deleteHabitBy(habitId)
            .onSuccess(onSuccess)
            .onFailure(::handleError)
    }

    private fun handleError(error: Throwable) {
        val message = error.message ?: "An error occurred"
        emit(GoodHabitEffect.ShowError(error.message ?: "An error occurred"))
        Logger.e(message, error)
    }
}
