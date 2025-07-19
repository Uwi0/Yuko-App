package com.kakapo.model.heatmap

import kotlinx.datetime.LocalDate
import kotlin.native.ObjCName

sealed class CompletionDayModel {
    data class Day(
        @ObjCName("dateKt")
        val date: LocalDate,
        val count: Int,
        @ObjCName("levelKt")
        val level: Int
    ): CompletionDayModel()
    object Empty : CompletionDayModel()
}
