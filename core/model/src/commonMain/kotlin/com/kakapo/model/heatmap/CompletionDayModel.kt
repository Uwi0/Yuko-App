package com.kakapo.model.heatmap

import kotlinx.datetime.LocalDate
import kotlin.native.ObjCName

data class CompletionDayModel(
    @ObjCName("dateKt")
    val date: LocalDate,
    val count: Int,
    @ObjCName("levelKt")
    val level: Int
)