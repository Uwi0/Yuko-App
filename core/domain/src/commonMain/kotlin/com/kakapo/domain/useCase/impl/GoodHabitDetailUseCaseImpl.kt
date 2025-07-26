package com.kakapo.domain.useCase.impl

import com.kakapo.data.repository.base.habit.HabitCheckRepository
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.domain.model.GoodHabitUseCaseParam
import com.kakapo.domain.useCase.base.GoodHabitDetailUseCase
import com.kakapo.domain.useCase.logic.toGoodHabitWith
import com.kakapo.model.habit.GoodHabitModel
import kotlinx.coroutines.flow.first

class GoodHabitDetailUseCaseImpl(
    private val habitRepository: HabitRepository,
    private val habitCheckRepository: HabitCheckRepository,
): GoodHabitDetailUseCase {

    override suspend fun execute(param: GoodHabitUseCaseParam): Result<GoodHabitModel> {
        return habitRepository.loadHabitDetailBy(param.habitId).mapCatching { habit ->
            val habitChecks = habitCheckRepository.loadHabitChecksBy(param.habitId).first()
            habit.toGoodHabitWith(habitChecks, param)
        }
    }
}