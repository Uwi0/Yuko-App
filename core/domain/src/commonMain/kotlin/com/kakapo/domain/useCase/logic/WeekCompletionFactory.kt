package com.kakapo.domain.useCase.logic

import com.kakapo.model.date.WeekModel

fun WeekModel.asCompletionValue(completion: Map<Long, Boolean>): List<Double> {
    return this.dates.map { day ->
        val dayEpoch = day.toEpochDays()
        val isComplete = completion[dayEpoch] ?: false
        if (isComplete) 1.0 else 0.0
    }
}