package org.kakapo.project.presentation.habitMenu.addHabit

import org.kakapo.project.presentation.habitMenu.habits.HabitsState
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("AddHabitViewModelKt")
class AddHabitViewModel(

): BaseViewModel<AddHabitState, AddHabitEffect, AddHabitEvent>(AddHabitState()) {

    override fun handleEvent(event: AddHabitEvent) {
        when(event) {
            AddHabitEvent.NavigateBack -> emit(AddHabitEffect.NavigateBack)
        }
    }
}