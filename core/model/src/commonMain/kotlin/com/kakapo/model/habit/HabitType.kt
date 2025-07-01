package com.kakapo.model.habit

enum class HabitType {
    GOOD, BAD
}

fun HabitType.toLong(): Long {
    return when (this) {
        HabitType.GOOD -> 1
        HabitType.BAD -> 0
    }
}

fun Long.toHabitType(): HabitType {
    return when (this) {
        1L -> HabitType.GOOD
        0L -> HabitType.BAD
        else -> throw IllegalArgumentException("Invalid HabitType value: $this")
    }
}


