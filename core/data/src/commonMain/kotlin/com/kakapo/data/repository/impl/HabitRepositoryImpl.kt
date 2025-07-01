package com.kakapo.data.repository.impl

import com.kakapo.common.util.todayAtMidnight
import com.kakapo.data.model.HabitParam
import com.kakapo.data.model.toHabitModel
import com.kakapo.data.repository.base.HabitRepository
import com.kakapo.database.datasource.base.HabitLocalDatasource
import com.kakapo.database.model.HabitEntity
import com.kakapo.model.HabitModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(
    private val habitLocalDatasource: HabitLocalDatasource
) : HabitRepository {

    override suspend fun save(habit: HabitParam): Result<Unit> {
        val entity = habit.toEntity()
        return habitLocalDatasource.insertHabit(entity)
    }

    override fun loadHabitsToday(): Flow<List<HabitModel>> {
        val toHabits: (List<HabitEntity>) -> List<HabitModel> = { habits ->
            habits.map(HabitEntity::toHabitModel)
        }
        return habitLocalDatasource.getHabits(todayAtMidnight).map(toHabits)
    }
}