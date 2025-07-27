package org.kakapo.project.presentation.habitMenu.addHabit

import androidx.lifecycle.viewModelScope
import com.kakapo.data.repository.base.habit.HabitRepository
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.habitMenu.habits.HabitsState
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("AddHabitViewModelKt")
class AddHabitViewModel(
    private val habitRepository: HabitRepository
): BaseViewModel<AddHabitState, AddHabitEffect, AddHabitEvent>(AddHabitState()) {

    override fun handleEvent(event: AddHabitEvent) {
        when(event) {
            is AddHabitEvent.DescriptionChanged -> _uiState.update { it.copy(description = event.description) }
            is AddHabitEvent.NameChanged -> _uiState.update { it.copy(name = event.name) }
            is AddHabitEvent.QuantityChanged -> _uiState.update { it.copy(targetFrequency = event.frequency) }
            AddHabitEvent.NavigateBack -> emit(AddHabitEffect.NavigateBack)
            AddHabitEvent.ToggleType -> _uiState.update { it.toggleType() }
            AddHabitEvent.SaveHabit -> saveHabit()
        }
    }

    private fun saveHabit() = viewModelScope.launch {
        val habitParam = _uiState.value.asHabitParam()
        val onSuccess: (Unit) -> Unit = {
            emit(AddHabitEffect.NavigateBack)
        }
        habitRepository.save(habitParam)
            .onSuccess(onSuccess)
            .onFailure(::handleError)
    }

    private fun handleError(throwable: Throwable?) {
        val errorMessage = throwable?.message ?: "An unknown error occurred"
        emit(AddHabitEffect.ShowError(errorMessage))
    }
}