package com.kakapo.domain.useCase.impl

import com.kakapo.common.util.toDateWith
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

    private fun HabitModel.toGoodHabitWith(
        habitChecks: List<HabitCheckModel>,
        param: GoodHabitUseCaseParam
    ): GoodHabitModel {
        val checksThisMonthUntilToday = habitChecks.filter { completion ->
            completion.date >= param.startOfMonth && completion.date <= param.currentDay
        }
        val allDaysThisMonth = generateDaysInRange(param.startOfMonth, param.currentDay)
        val checkedDates = checksThisMonthUntilToday.map { it.date }.toSet()
        val calendarMap = generateCalendarMap(habitChecks, startDate, param.currentDay)
        val missedCount = allDaysThisMonth.count { date ->
            date !in checkedDates && date >= startDate
        }

        return GoodHabitModel(
            name = name,
            description = description,
            missedCount = missedCount,
            currentStreak = calendarMap.asCurrentStreak(param.currentDay),
            totalComplete = habitChecks.size,
            bestStreak = calculateBestStreak(habitChecks, startDate, param.currentDay),
            completionThisMonth = checksThisMonthUntilToday.size,
            startDate = startDate.toDateWith(format = "dd MMM yyyy"),
            calendarMap = calendarMap
        )
    }

    private fun generateDaysInRange(startDate: Long, endDate: Long): List<Long> {
        val dayCount = ((endDate - startDate) / MILLIS_IN_A_DAY).toInt() + 1
        return (0 until dayCount).map { i ->
            startDate + (i * MILLIS_IN_A_DAY)
        }
    }

    private fun generateCalendarMap(
        habitChecks: List<HabitCheckModel>,
        habitStartDate: Long,
        currentDay: Long
    ): Map<Long, Boolean> {
        val checkedDates = habitChecks.map { it.date }.toSet()
        val allDays = generateDaysInRange(habitStartDate, currentDay)

        return allDays.associateWith { date ->
            date in checkedDates
        }
    }

    private fun calculateBestStreak(
        habitChecks: List<HabitCheckModel>,
        habitStartDate: Long,
        currentDay: Long
    ): Int {
        val checkedDates = habitChecks.map { it.date }.toSet()
        val allDays = generateDaysInRange(habitStartDate, currentDay)

        return allDays
            .map { it in checkedDates }
            .scan(0) { currentStreak, isCompleted ->
                if (isCompleted) currentStreak + 1 else 0
            }
            .maxOrNull() ?: 0
    }

    private fun Map<Long, Boolean>.asCurrentStreak(todayAtMidnight: Long): Int {
        return generateSequence(todayAtMidnight) { it - MILLIS_IN_A_DAY }
            .takeWhile { this[it] == true }
            .count()
    }

    companion object {
        const val MILLIS_IN_A_DAY: Long = 24 * 60 * 60 * 1000L
    }
}