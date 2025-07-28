package com.kakapo.domain.useCase.logic

import com.kakapo.common.util.dayToDateWith
import com.kakapo.domain.model.GoodHabitUseCaseParam
import com.kakapo.model.habit.CompletionType
import com.kakapo.model.habit.GoodHabitModel
import com.kakapo.model.habit.HabitCheckModel
import com.kakapo.model.habit.HabitModel

fun HabitModel.toGoodHabitWith(
    habitChecks: List<HabitCheckModel>,
    param: GoodHabitUseCaseParam
): GoodHabitModel {
    return GoodHabitDetailLogic.buildGoodHabitModel(this, habitChecks, param)
}

internal object GoodHabitDetailLogic {

    fun buildGoodHabitModel(
        habitModel: HabitModel,
        habitChecks: List<HabitCheckModel>,
        param: GoodHabitUseCaseParam
    ): GoodHabitModel {
        val checksThisMonthUntilToday = habitChecks.filterThisMonthUntilToday(
            param.startOfMonth,
            param.currentDay
        )

        val allDaysThisMonth = generateDaysInRange(
            param.startOfMonth,
            param.currentDay
        )

        val calendarMap = generateCalendarMap(
            habitChecks,
            habitModel.startDate,
            param.currentDay
        )

        val completionThisMonth = when (habitModel.completionType) {
            CompletionType.Single -> checksThisMonthUntilToday.size
            CompletionType.Frequency -> checksThisMonthUntilToday.sumOf { it.completionCount }.toInt()
        }

        val missedCount = calculateMissedCount(
            allDaysThisMonth,
            calendarMap,
            habitModel.startDate,
            habitModel.completionType,
            habitModel.targetFrequency.toInt()
        )

        val totalComplete =  when (habitModel.completionType) {
            CompletionType.Single -> habitChecks.size
            CompletionType.Frequency -> habitChecks.sumOf { it.completionCount }.toInt()
        }

        val currentStreak = calendarMap.countStreakUp(
            habitModel.completionType,
            habitModel.targetFrequency.toInt()
        )

        val bestStreak = calculateBestStreak(
            habitChecks,
            habitModel.startDate,
            param.currentDay,
            habitModel.completionType,
            habitModel.targetFrequency.toInt()
        )

        return GoodHabitModel(
            name = habitModel.name,
            description = habitModel.description,
            missedCount = missedCount,
            currentStreak = currentStreak,
            totalComplete = totalComplete,
            bestStreak = bestStreak,
            completionThisMonth = completionThisMonth,
            startDate = habitModel.startDate,
            calendarMap = calendarMap
        )
    }

    fun List<HabitCheckModel>.filterThisMonthUntilToday(
        startDate: Long,
        endDate: Long
    ): List<HabitCheckModel> {
        return filter { it.date in startDate..endDate }
    }

    fun generateDaysInRange(startDate: Long, endDate: Long): List<Long> {
        val dayCount = (endDate - startDate).toInt() + 1
        return if (dayCount <= 0) emptyList()
        else (0 until dayCount).map { i -> startDate + i }
    }

    fun generateCalendarMap(
        habitChecks: List<HabitCheckModel>,
        habitStartDate: Long,
        currentDay: Long
    ): Map<Long, Int> {
        if (habitChecks.isEmpty()) return emptyMap()

        // Group by date and sum completion counts
        val checksByDate = habitChecks.groupBy { it.date }
            .mapValues { (_, checks) ->
                checks.sumOf { it.completionCount }
            }

        val allDays = generateDaysInRange(habitStartDate, currentDay)
        return allDays.associateWith { date ->
            (checksByDate[date] ?: 0).toInt()
        }
    }
    fun calculateBestStreak(
        habitChecks: List<HabitCheckModel>,
        habitStartDate: Long,
        currentDay: Long,
        completionType: CompletionType,
        targetFrequency: Int
    ): Int {
        if (habitChecks.isEmpty()) return 0

        val calendarMap = generateCalendarMap(habitChecks, habitStartDate, currentDay)
        val allDays = generateDaysInRange(habitStartDate, currentDay)

        return allDays
            .map { date ->
                val count = calendarMap[date] ?: 0
                when (completionType) {
                    CompletionType.Single -> count >= 1
                    CompletionType.Frequency -> count >= targetFrequency
                }
            }
            .scan(0) { currentStreak, isCompleted ->
                if (isCompleted) currentStreak + 1 else 0
            }
            .maxOrNull() ?: 0
    }

    fun Map<Long, Int>.countStreakUp(
        completionType: CompletionType,
        targetFrequency: Int
    ): Int {
        return this.keys.sorted()
            .reversed()
            .takeWhile { date ->
                val count = this[date] ?: 0
                when (completionType) {
                    CompletionType.Single -> count >= 1
                    CompletionType.Frequency -> count >= targetFrequency
                }
            }
            .count()
    }
    fun calculateMissedCount(
        allDaysThisMonth: List<Long>,
        calendarMap: Map<Long, Int>,
        habitStartDate: Long,
        completionType: CompletionType,
        targetFrequency: Int
    ): Int = allDaysThisMonth.count { date ->
        if (date < habitStartDate) return@count false

        val count = calendarMap[date] ?: 0
        when (completionType) {
            CompletionType.Single -> count == 0
            CompletionType.Frequency -> count < targetFrequency
        }
    }
}