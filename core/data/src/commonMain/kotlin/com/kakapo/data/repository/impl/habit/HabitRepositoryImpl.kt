package com.kakapo.data.repository.impl.habit

import com.kakapo.data.model.habit.HabitParam
import com.kakapo.data.model.habit.toHabitDetailModel
import com.kakapo.data.model.habit.toHabitModel
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.database.datasource.base.habits.HabitLocalDatasource
import com.kakapo.database.model.habit.HabitEntity
import com.kakapo.model.habit.HabitItemModel
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

    override suspend fun loadHabitDetailBy(id: Long): Result<HabitModel> {
        return habitLocalDatasource
            .getHabitBy(id)
            .mapCatching { it.toHabitDetailModel() }
    }

    override fun loadHabits(today: Long): Flow<List<HabitItemModel>> {
        val toHabits: (List<HabitEntity>) -> List<HabitItemModel> = { habits ->
            habits.map(HabitEntity::toHabitModel)
        }
        return habitLocalDatasource
            .getHabits(today)
            .map(toHabits)
    }
}