package com.kakapo.domain.useCase.impl

import com.kakapo.data.repository.base.habit.HabitCheckRepository
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.domain.model.GoodHabitUseCaseParam
import com.kakapo.domain.useCase.base.GoodHabitDetailUseCase
import com.kakapo.model.habit.GoodHabitModel
import com.kakapo.model.habit.HabitCheckModel
import com.kakapo.model.habit.HabitModel
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

    private fun HabitModel.toGoodHabitWith(habitChecks: List<HabitCheckModel>, param: GoodHabitUseCaseParam): GoodHabitModel {
        val thisMonth = habitChecks.filter { it.date >= param.startOfMonth && it.date <= param.endOfMonth }
        val calendarMap = habitChecks.associate { it.date to it.isCompleted }
        return GoodHabitModel(
            name = name,
            description = description,
            missedCount = thisMonth.count { !it.isCompleted },
            currentStreak = calendarMap.asCurrentStreak(param.dayAtMidnight),
            totalComplete = habitChecks.count { it.isCompleted },
            bestStreak = habitChecks.count(),
            completionThisMonth = thisMonth.count { it.isCompleted },
            startDate = startDate,
            calendarMap = calendarMap
        )
    }

    fun Map<Long, Boolean>.asCurrentStreak(todayAtMidnight: Long): Int {
        return generateSequence(todayAtMidnight) { it - MILLIS_IN_A_DAY }
            .takeWhile { this[it] == true }
            .count()
    }

    companion object {
        const val MILLIS_IN_A_DAY: Int = 24 * 60 * 60 * 1000
    }
}