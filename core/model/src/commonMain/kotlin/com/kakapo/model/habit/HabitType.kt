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

fun String.toHabitType(): HabitType {
    return when (this) {
        HabitType.GOOD.name -> HabitType.GOOD
        HabitType.BAD.name -> HabitType.BAD
        else -> throw IllegalArgumentException("Invalid HabitType value: $this")
    }
}


