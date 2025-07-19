package com.kakapo.model.heatmap

import kotlin.native.ObjCName

data class MonthInfoModel(
    @ObjCName("monthIndexKt")
    val monthIndex: Int,
    @ObjCName("widthKt")
    val width: Float,
    val startWeek: Int,
    val endWeek: Int
)