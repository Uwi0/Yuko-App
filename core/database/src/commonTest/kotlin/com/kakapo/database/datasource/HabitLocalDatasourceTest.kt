package com.kakapo.database.datasource

import app.cash.sqldelight.db.SqlDriver
import app.cash.turbine.test
import com.kakapo.common.util.today
import com.kakapo.database.createInMemoryDatabase
import com.kakapo.database.datasource.base.habits.HabitCheckLocalDatasource
import com.kakapo.database.datasource.base.habits.HabitLocalDatasource
import com.kakapo.database.datasource.implementation.habit.HabitCheckLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.habit.HabitLocalDatasourceImpl
import com.kakapo.database.dummyHabit
import com.kakapo.database.dummyHabit2
import com.kakapo.database.model.habit.HabitEntity
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class HabitLocalDatasourceTest {

    private lateinit var sqlDriver: SqlDriver
    private lateinit var habitDatasource: HabitLocalDatasource
    private lateinit var habitCheckLocalDatasource: HabitCheckLocalDatasource
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        sqlDriver = createInMemoryDatabase()
        habitDatasource = HabitLocalDatasourceImpl(sqlDriver, testDispatcher)
        habitCheckLocalDatasource = HabitCheckLocalDatasourceImpl(sqlDriver, testDispatcher)
    }

    @Test
    fun `getHabitBy id should return correct data`() = runTest(testDispatcher) {
        habitDatasource.insertHabit(dummyHabit)
        val result = habitDatasource.getHabitBy(1L)
        val resultValue = result.getOrElse { HabitEntity() }
        assertIs<Boolean>(result.isSuccess)
        assertIs<Boolean>(result.getOrNull() == dummyHabit)
        resultValue.name shouldBe dummyHabit.name
    }

    @Test
    fun `delete habit should remove habit form db`() = runTest(testDispatcher) {
        habitDatasource.insertHabit(dummyHabit)
        habitDatasource.deleteHabitBy(1L)
        val result = habitDatasource.getHabitBy(1L)
        assertIs<Boolean>(result.isFailure)
    }

    @Test
    fun `get habit today must return at least some complete today`() = runTest(testDispatcher) {
        habitDatasource.insertHabit(dummyHabit)
        habitDatasource.insertHabit(dummyHabit2)
        habitCheckLocalDatasource.insertTodayCheck(dummyHabit.id, today)
        habitDatasource.getHabits(today).test {
            val result = awaitItem()
            result.size shouldBe 2
            println(result)
            result.first().isCompletedToday shouldBe true
            result[1].isCompletedToday shouldBe false
            cancelAndConsumeRemainingEvents()
        }
    }
}