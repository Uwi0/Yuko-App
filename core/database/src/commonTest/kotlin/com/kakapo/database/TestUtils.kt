package com.kakapo.database

import app.cash.sqldelight.db.SqlDriver
import com.kakapo.database.model.habit.HabitCheckEntity
import com.kakapo.database.model.habit.HabitEntity
import kotlinx.datetime.LocalDate
import kotlin.time.Clock

expect fun createInMemoryDatabase(): SqlDriver

val dummyHabit = HabitEntity(
    id = 1L,
    name = "Read a book",
    description = "Read 12 times this month",
    frequency = 12L,
    completionType = "",
    habitType = "GoodHabit",
    startDate = LocalDate(2025, 7, 1).toEpochDays(),
    isArchived = false,
    createdAt = LocalDate(2025, 7, 1).toEpochDays(),
    updatedAt = Clock.System.now().toEpochMilliseconds(),
    isCompletedToday = false,
    lastSlipDate = null
)

val dummyHabit2 = HabitEntity(
    id = 1L,
    name = "run every morning",
    description = "at least 4 times this month",
    frequency = 4L,
    completionType = "",
    habitType = "GoodHabit",
    startDate = LocalDate(2025, 7, 1).toEpochDays(),
    isArchived = false,
    createdAt = LocalDate(2025, 7, 1).toEpochDays(),
    updatedAt = Clock.System.now().toEpochMilliseconds(),
    isCompletedToday = false,
    lastSlipDate = null
)

val dummyHabitCheck = HabitCheckEntity(
    id = 1L,
    habitId = dummyHabit.id,
    date = LocalDate(2025, 7, 1).toEpochDays(),
    isCompleted = true
)