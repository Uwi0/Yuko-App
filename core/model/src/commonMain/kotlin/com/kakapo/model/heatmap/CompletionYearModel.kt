package com.kakapo.model.heatmap

import kotlin.native.ObjCName

data class CompletionYearModel(
    @ObjCName("yearKt")
    val year: Int,
    val weeks: List<CompletionWeekModel>,
    @ObjCName("totalCompletionsKt")
    val totalCompletions: Int,
)
