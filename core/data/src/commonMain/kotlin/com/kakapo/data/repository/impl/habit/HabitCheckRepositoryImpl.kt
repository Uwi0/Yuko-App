package com.kakapo.data.repository.impl.habit

import com.kakapo.data.model.habit.toHabitCheckModel
import com.kakapo.data.repository.base.habit.HabitCheckRepository
import com.kakapo.database.datasource.base.habits.HabitCheckLocalDatasource
import com.kakapo.database.model.habit.HabitCheckEntity
import com.kakapo.model.habit.HabitCheckModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitCheckRepositoryImpl(
    private val habitCheckLocalDatasource: HabitCheckLocalDatasource
) : HabitCheckRepository {

    override suspend fun saveTodayCheckBy(
        habitId: Long,
        date: Long,
    ): Result<Unit> {
        return habitCheckLocalDatasource.insertTodayCheck(habitId, date)
    }

    override suspend fun deleteTodayCheckBy(
        habitId: Long,
        date: Long,
    ): Result<Unit> {
        return habitCheckLocalDatasource.deleteTodayCheck(habitId, date)
    }

    override fun loadHabitChecksBy(habitId: Long): Flow<List<HabitCheckModel>> {
        val toHabitCheks: (List<HabitCheckEntity>) -> List<HabitCheckModel> = { habits ->
            habits.map(HabitCheckEntity::toHabitCheckModel)
        }

        return habitCheckLocalDatasource
            .getHabitCheckBy(habitId)
            .map(toHabitCheks)
    }
}