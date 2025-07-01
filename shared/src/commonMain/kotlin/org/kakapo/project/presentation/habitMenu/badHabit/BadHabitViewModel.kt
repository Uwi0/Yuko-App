package org.kakapo.project.presentation.habitMenu.badHabit

import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.HabitRepository
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("BadHabitViewModelKt")
class BadHabitViewModel(
    private val habitRepository: HabitRepository
): BaseViewModel<BadHabitState, BadHabitEffect, BadHabitEvent>(BadHabitState()) {

    override fun handleEvent(event: BadHabitEvent) {
        when(event) {
            BadHabitEvent.DeleteHabit -> deleteHabit()
            BadHabitEvent.NavigateBack -> emit(BadHabitEffect.NavigateBack)
        }
    }

    private var habitId: Long = 0L

    fun initData(id: Long) {
        habitId = id
    }

    private fun deleteHabit() = viewModelScope.launch {
        val onSuccess: (Unit) -> Unit = {
            emit(BadHabitEffect.NavigateBack)
        }

        habitRepository.deleteHabitBy(habitId)
            .onSuccess(onSuccess)
            .onFailure(::handleError)
    }

    private fun handleError(error: Throwable) {
        emit(BadHabitEffect.ShowError(error.message ?: "An error occurred"))
    }
}