package com.kakapo.domain.model

import com.kakapo.model.habit.HabitCheckModel
import com.kakapo.model.habit.HabitModel
import kotlinx.datetime.LocalDate

object GoodHabitTestData {
    val habitStartDateEpoch = epochDayToMillis(1)
    val currentDayEpoch = epochDayToMillis(7)

    fun epochDayToMillis(day: Int): Long {
        return LocalDate(2025, 7, day).toEpochDays() * 24 * 60 * 60 * 1000L
    }

    fun habitChecksForJuly_1_2_4_6(): List<HabitCheckModel> {
        return listOf(1, 2, 4, 6).mapIndexed { index, day ->
            HabitCheckModel(
                id = index.toLong(),
                date = epochDayToMillis(day),
                isCompleted = true
            )
        }
    }
}
