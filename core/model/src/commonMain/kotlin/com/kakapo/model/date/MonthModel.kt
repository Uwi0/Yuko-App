package com.kakapo.model.date

import kotlinx.datetime.LocalDate
import kotlin.native.ObjCName

data class MonthModel(
    @ObjCName("monthId")
    val id: Int,
    val weeks: List<WeekOfMonthModel>
)
data class WeekOfMonthModel(
    @ObjCName("weekId")
    val id: Int,
    val days: List<DayState>
)

sealed class DayState {
    object Empty : DayState()
    data class Day(val date: LocalDate) : DayState()
}

sealed class DayValue {
    object Empty : DayValue()
    data class Day(val value: Double): DayValue()
}