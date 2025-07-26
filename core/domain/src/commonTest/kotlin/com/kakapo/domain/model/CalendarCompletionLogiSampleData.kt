package com.kakapo.domain.model

import com.kakapo.model.date.DayState
import com.kakapo.model.date.MonthModel
import com.kakapo.model.date.WeekOfMonthModel
import kotlinx.datetime.LocalDate

val completion: Map<Long, Boolean> = mapOf(
    20293L to true,
    20294L to true,
    20295L to true
)

val model = MonthModel(
    id = 7,
    weeks = listOf(
        WeekOfMonthModel(
            id = 0,
            days = listOf(
                DayState.Empty, DayState.Empty,
                DayState.Day(LocalDate.parse("2025-07-01")),
                DayState.Day(LocalDate.parse("2025-07-02")),
                DayState.Day(LocalDate.parse("2025-07-03")),
                DayState.Day(LocalDate.parse("2025-07-04")),
                DayState.Day(LocalDate.parse("2025-07-05")),
            )
        ),
        WeekOfMonthModel(
            id = 1,
            days = listOf(
                DayState.Day(LocalDate.parse("2025-07-06")),
                DayState.Day(LocalDate.parse("2025-07-07")),
                DayState.Day(LocalDate.parse("2025-07-08")),
                DayState.Day(LocalDate.parse("2025-07-09")),
                DayState.Day(LocalDate.parse("2025-07-10")),
                DayState.Day(LocalDate.parse("2025-07-11")),
                DayState.Day(LocalDate.parse("2025-07-12")),
            )
        ),
        WeekOfMonthModel(
            id = 2,
            days = listOf(
                DayState.Day(LocalDate.parse("2025-07-13")),
                DayState.Day(LocalDate.parse("2025-07-14")),
                DayState.Day(LocalDate.parse("2025-07-15")),
                DayState.Day(LocalDate.parse("2025-07-16")),
                DayState.Day(LocalDate.parse("2025-07-17")),
                DayState.Day(LocalDate.parse("2025-07-18")),
                DayState.Day(LocalDate.parse("2025-07-19")),
            )
        ),
        WeekOfMonthModel(
            id = 3,
            days = listOf(
                DayState.Day(LocalDate.parse("2025-07-20")),
                DayState.Day(LocalDate.parse("2025-07-21")),
                DayState.Day(LocalDate.parse("2025-07-22")),
                DayState.Day(LocalDate.parse("2025-07-23")),
                DayState.Day(LocalDate.parse("2025-07-24")),
                DayState.Day(LocalDate.parse("2025-07-25")),
                DayState.Day(LocalDate.parse("2025-07-26")),
            )
        ),
        WeekOfMonthModel(
            id = 4,
            days = listOf(
                DayState.Day(LocalDate.parse("2025-07-27")),
                DayState.Day(LocalDate.parse("2025-07-28")),
                DayState.Day(LocalDate.parse("2025-07-29")),
                DayState.Day(LocalDate.parse("2025-07-30")),
                DayState.Day(LocalDate.parse("2025-07-31")),
                DayState.Empty, DayState.Empty
            )
        ),
        WeekOfMonthModel(
            id = 5,
            days = List(7) { DayState.Empty }
        )
    )
)

val weekOfMonthModel = WeekOfMonthModel(
    id = 3,
    days = listOf(
        DayState.Day(LocalDate.parse("2025-07-20")),
        DayState.Day(LocalDate.parse("2025-07-21")),
        DayState.Day(LocalDate.parse("2025-07-22")),
        DayState.Day(LocalDate.parse("2025-07-23")),
        DayState.Day(LocalDate.parse("2025-07-24")),
        DayState.Day(LocalDate.parse("2025-07-25")),
        DayState.Day(LocalDate.parse("2025-07-26")),
    )
)