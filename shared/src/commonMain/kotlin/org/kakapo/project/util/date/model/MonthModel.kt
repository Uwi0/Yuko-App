package org.kakapo.project.util.date.model

import kotlin.native.ObjCName

data class MonthModel(
    @ObjCName("monthId")
    val id: Int,
    val weeks: List<WeekModel>
)
