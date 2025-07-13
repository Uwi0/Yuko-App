package org.kakapo.project.util.date.model

import kotlinx.datetime.LocalDate
import kotlin.native.ObjCName

data class WeekModel(
    @ObjCName("weekId")
    val id: Int,
    @ObjCName("weekDates")
    val dates: List<LocalDate>,
)
