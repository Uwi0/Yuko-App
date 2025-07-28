package com.kakapo.domain.model

import com.kakapo.model.habit.HabitCheckModel
import kotlinx.datetime.LocalDate

object GoodHabitTestData {
    val habitStartDateEpoch = epochDay(1)
    val currentDayEpoch = epochDay(7)

    fun epochDay(day: Int): Long {
        return LocalDate(2025, 7, day).toEpochDays()
    }

    fun habitChecksForJuly_1_2_4_6(): List<HabitCheckModel> {
        return listOf(1, 2, 4, 6).mapIndexed { index, day ->
            HabitCheckModel(
                id = index.toLong(),
                date = epochDay(day),
                isCompleted = true,
                completionCount = 1
            )
        }
    }
}
