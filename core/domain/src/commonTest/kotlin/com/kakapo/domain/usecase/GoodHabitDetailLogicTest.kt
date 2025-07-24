package com.kakapo.domain.usecase

import com.kakapo.domain.model.GoodHabitTestData
import com.kakapo.domain.model.GoodHabitUseCaseParam
import com.kakapo.domain.useCase.logic.GoodHabitDetailLogic
import com.kakapo.domain.useCase.logic.GoodHabitDetailLogic.calculateMissedCount
import com.kakapo.model.habit.HabitCheckModel
import com.kakapo.model.habit.HabitModel
import io.kotest.matchers.maps.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GoodHabitDetailLogicTest {

    @Test
    fun testGeneratedCalendarMap_withSampleChecks() {
        val habitChecks = GoodHabitTestData.habitChecksForJuly_1_2_4_6()
        val startDate = GoodHabitTestData.habitStartDateEpoch
        val currentDay = GoodHabitTestData.currentDayEpoch

        val result = GoodHabitDetailLogic.generateCalendarMap(habitChecks, startDate, currentDay)

        result.size shouldBe 7

        val expected = mapOf(
            GoodHabitTestData.epochDay(1) to true,
            GoodHabitTestData.epochDay(2) to true,
            GoodHabitTestData.epochDay(3) to false,
            GoodHabitTestData.epochDay(4) to true,
            GoodHabitTestData.epochDay(5) to false,
            GoodHabitTestData.epochDay(6) to true,
            GoodHabitTestData.epochDay(7) to false,
        )

        result shouldContainAll expected
    }

    @Test
    fun `generatedDaysInRange should return all days inclusive between start and end`() {
        val startDate = GoodHabitTestData.epochDay(1)
        val endDate = GoodHabitTestData.epochDay(3)

        val result = GoodHabitDetailLogic.generateDaysInRange(startDate, endDate)

        result.size shouldBe 3
        result shouldBe listOf(
            GoodHabitTestData.epochDay(1),
            GoodHabitTestData.epochDay(2),
            GoodHabitTestData.epochDay(3),
        )
    }

    @Test
    fun `calculateBestStreak should return the longest sequence of continuous true days`() {
        val checks = listOf(1, 2, 4, 5, 6).mapIndexed { index, day ->
            HabitCheckModel(
                id = index.toLong(),
                date = GoodHabitTestData.epochDay(day),
                isCompleted = true
            )
        }

        val bestStreak = GoodHabitDetailLogic.calculateBestStreak(
            checks,
            GoodHabitTestData.epochDay(1),
            GoodHabitTestData.epochDay(7)
        )

        bestStreak shouldBe 3
    }

    @Test
    fun `asCurrentStreak should count how many days streak up to today`() {
        val today = GoodHabitTestData.epochDay(7)
        val map = mapOf(
            GoodHabitTestData.epochDay(7) to true,
            GoodHabitTestData.epochDay(6) to true,
            GoodHabitTestData.epochDay(5) to false,
            GoodHabitTestData.epochDay(4) to true
        )

        val currentStreak = with(GoodHabitDetailLogic) {
            map.countStreakUp()
        }

        currentStreak shouldBe 2
    }

    @Test
    fun `calculateMissedCount should return correct number of missed days`() {
        val epoch: (Int) -> Long = { day -> GoodHabitTestData.epochDay(day) }
        val all = listOf(epoch(1), epoch(2), epoch(3), epoch(4), epoch(5))
        val checked = setOf(epoch(1), epoch(3))
        val startDate = epoch(1)

        val result = calculateMissedCount(
            all,
            checked,
            startDate
        )

        result shouldBe 3
    }

    @Test
    fun `buildGoodHabitModel should return correct result for mixed completion`() {
        val habit = HabitModel(
            id = 1,
            name = "Habit",
            description = "Description",
            startDate = GoodHabitTestData.habitStartDateEpoch
        )

        val checks = listOf(1, 2, 4, 7).mapIndexed { index, day ->
            HabitCheckModel(
                id = index.toLong(),
                date = GoodHabitTestData.epochDay(day),
                isCompleted = true
            )
        }

        val param = GoodHabitUseCaseParam(
            habitId = 1,
            currentDay = GoodHabitTestData.currentDayEpoch,
            startOfMonth = GoodHabitTestData.habitStartDateEpoch,
        )

        val result = GoodHabitDetailLogic.buildGoodHabitModel(habit, checks, param)
        result.missedCount shouldBe 3
        result.currentStreak shouldBe 1
        result.totalComplete shouldBe 4
        result.bestStreak shouldBe 2
        result.completionThisMonth shouldBe 4
        result.startDate shouldBe "01 Jul 2025"
        result.calendarMap.size shouldBe 7
    }
}