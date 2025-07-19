package com.kakapo.model.date

import kotlinx.datetime.LocalDate
import kotlin.native.ObjCName

data class WeekModel(
    @ObjCName("weekId")
    val id: Int,
    @ObjCName("weekDates")
    val dates: List<LocalDate>,
)
