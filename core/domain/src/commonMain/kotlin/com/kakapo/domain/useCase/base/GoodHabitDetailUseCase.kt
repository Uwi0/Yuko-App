package com.kakapo.domain.useCase.base

import com.kakapo.domain.model.GoodHabitUseCaseParam
import com.kakapo.model.habit.GoodHabitModel

interface GoodHabitDetailUseCase {
    suspend fun execute(param: GoodHabitUseCaseParam): Result<GoodHabitModel>
}