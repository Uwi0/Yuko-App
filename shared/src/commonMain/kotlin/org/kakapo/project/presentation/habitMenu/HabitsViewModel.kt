package org.kakapo.project.presentation.habitMenu

import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("HabitsViewModelKt")
class HabitsViewModel: BaseViewModel<HabitsState, HabitsEffect, HabitsEvent>(HabitsState()) {

    override fun handleEvent(event: HabitsEvent) {
        when(event) {
            HabitsEvent.TapToAddHabit -> emit(HabitsEffect.TapToAddHabit)
            HabitsEvent.NavigateBack -> emit(HabitsEffect.NavigateBack)
        }
    }

}