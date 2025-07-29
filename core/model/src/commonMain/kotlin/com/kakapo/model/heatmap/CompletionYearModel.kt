package com.kakapo.model.heatmap

import kotlin.native.ObjCName

data class CompletionYearModel(
    @ObjCName("yearKt")
    val year: Int = 0,
    val weeks: List<CompletionWeekModel> = emptyList(),
    @ObjCName("totalCompletionsKt")
    val totalCompletions: Int = 0,
)
