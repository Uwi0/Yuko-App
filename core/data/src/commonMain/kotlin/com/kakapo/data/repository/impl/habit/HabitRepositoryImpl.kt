package com.kakapo.data.repository.impl.habit

import com.kakapo.common.util.todayAtMidnight
import com.kakapo.data.model.habit.HabitParam
import com.kakapo.data.model.habit.toHabitDetailModel
import com.kakapo.data.model.habit.toHabitModel
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.database.datasource.base.habits.HabitLocalDatasource
import com.kakapo.database.model.habit.HabitEntity
import com.kakapo.model.habit.HabitDetailModel
import com.kakapo.model.habit.HabitModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(
    private val habitLocalDatasource: HabitLocalDatasource
) : HabitRepository {

    override suspend fun save(habit: HabitParam): Result<Unit> {
        val entity = habit.toEntity()
        return habitLocalDatasource.insertHabit(entity)
    }

    override suspend fun deleteHabitBy(id: Long): Result<Unit> {
        return habitLocalDatasource.deleteHabitBy(id)
    }

    override suspend fun loadHabitDetailBy(id: Long): Result<HabitDetailModel> {
        return habitLocalDatasource
            .getHabitBy(id)
            .mapCatching { it.toHabitDetailModel() }
    }

    override fun loadHabitsToday(): Flow<List<HabitModel>> {
        val toHabits: (List<HabitEntity>) -> List<HabitModel> = { habits ->
            habits.map(HabitEntity::toHabitModel)
        }
        return habitLocalDatasource
            .getHabits(todayAtMidnight)
            .map(toHabits)
    }
}