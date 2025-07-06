package com.kakapo.database.datasource.base.habits

interface HabitCheckLocalDatasource {
    suspend fun insertTodayCheck(habitId: Long, date: Long): Result<Unit>
    suspend fun deleteTodayCheck(habitId: Long, date: Long): Result<Unit>
}