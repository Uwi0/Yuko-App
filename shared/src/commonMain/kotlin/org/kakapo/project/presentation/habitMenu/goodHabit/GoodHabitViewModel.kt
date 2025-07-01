package org.kakapo.project.presentation.habitMenu.goodHabit

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import org.kakapo.project.presentation.util.BaseViewModel
import kotlin.native.ObjCName

@ObjCName("GoodHabitViewModelKt")
class GoodHabitViewModel(
    private val habitRepository: com.kakapo.data.repository.base.HabitRepository
) : BaseViewModel<GoodHabitState, GoodHabitEffect, GoodHabitEvent>(GoodHabitState()) {

    override fun handleEvent(event: GoodHabitEvent) {
        when (event) {
            GoodHabitEvent.DeleteHabit -> deleteHabit()
            GoodHabitEvent.NavigateBack -> emit(GoodHabitEffect.NavigateBack)
        }
    }

    private var habitId: Long = 0L

    fun initData(id: Long) {
        habitId = id
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
        emit(GoodHabitEffect.ShowError(error.message ?: "An error occurred"))
    }
}
