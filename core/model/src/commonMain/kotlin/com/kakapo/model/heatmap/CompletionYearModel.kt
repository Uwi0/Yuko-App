package com.kakapo.model.heatmap

data class CompletionYearModel(
    val year: Int,
    val weeks: List<CompletionWeekModel>,
    val totalCompletions: Int,
)
