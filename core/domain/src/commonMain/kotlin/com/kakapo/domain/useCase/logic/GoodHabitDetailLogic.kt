package com.kakapo.domain.useCase.logic

import com.kakapo.common.util.dayToDateWith
import com.kakapo.domain.model.GoodHabitUseCaseParam
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
        val allDaysThisMonth = generateDaysInRange(param.startOfMonth, param.currentDay)
        val checkedDates = checksThisMonthUntilToday.map { it.date }.toSet()
        val calendarMap = generateCalendarMap(
            habitChecks,
            habitModel.startDate,
            param.currentDay
        )
        val missedCount = calculateMissedCount(
            allDaysThisMonth,
            checkedDates,
            habitModel.startDate
        )
        return GoodHabitModel(
            name = habitModel.name,
            description = habitModel.description,
            missedCount = missedCount,
            currentStreak = calendarMap.countStreakUp(),
            totalComplete = habitChecks.size,
            bestStreak = calculateBestStreak(habitChecks, habitModel.startDate, param.currentDay),
            completionThisMonth = checksThisMonthUntilToday.size,
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
    ): Map<Long, Boolean> {
        if (habitChecks.isEmpty()) return emptyMap()
        val checkedDates = habitChecks.map { it.date }.toSet()
        val allDays = generateDaysInRange(habitStartDate, currentDay)
        return allDays.associateWith { date ->
            date in checkedDates
        }
    }

    fun calculateBestStreak(
        habitChecks: List<HabitCheckModel>,
        habitStartDate: Long,
        currentDay: Long
    ): Int {
        if (habitChecks.isEmpty()) return 0

        val checkedDates = habitChecks.map { it.date }.toSet()
        val allDays = generateDaysInRange(habitStartDate, currentDay)

        return allDays
            .map { it in checkedDates }
            .scan(0) { currentStreak, isCompleted ->
                if (isCompleted) currentStreak + 1 else 0
            }
            .maxOrNull() ?: 0
    }

    fun Map<Long, Boolean>.countStreakUp(): Int {
        return this.keys.sorted()
            .reversed()
            .takeWhile { this[it] == true }
            .count()
    }

    fun calculateMissedCount(
        allDaysThisMonth: List<Long>,
        checkedDates: Set<Long>,
        habitStartDate: Long
    ): Int = allDaysThisMonth.count { date ->
        date !in checkedDates && date >= habitStartDate
    }
}