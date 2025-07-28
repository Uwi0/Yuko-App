package com.kakapo.domain.usecase

import com.kakapo.common.util.currentDay
import com.kakapo.domain.model.GoodHabitTestData
import com.kakapo.domain.model.GoodHabitUseCaseParam
import com.kakapo.domain.useCase.logic.GoodHabitDetailLogic
import com.kakapo.domain.useCase.logic.GoodHabitDetailLogic.calculateMissedCount
import com.kakapo.model.habit.CompletionType
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
            GoodHabitTestData.epochDay(1) to 1,
            GoodHabitTestData.epochDay(2) to 1,
            GoodHabitTestData.epochDay(3) to 0,
            GoodHabitTestData.epochDay(4) to 1,
            GoodHabitTestData.epochDay(5) to 0,
            GoodHabitTestData.epochDay(6) to 1,
            GoodHabitTestData.epochDay(7) to 0,
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
                isCompleted = true,
                completionCount = 1
            )
        }

        val bestStreak = GoodHabitDetailLogic.calculateBestStreak(
            checks,
            GoodHabitTestData.epochDay(1),
            GoodHabitTestData.epochDay(7),
            completionType = CompletionType.Single,
            targetFrequency = 1
        )

        bestStreak shouldBe 3
    }

    @Test
    fun `calculate best streak at first day should return at least one best steak`() {
        val checks = listOf(HabitCheckModel(
            id = 1,
            date = currentDay,
            isCompleted = true,
            completionCount = 1
        ))
        val bestStreak = GoodHabitDetailLogic.calculateBestStreak(
            checks,
            currentDay,
            currentDay,
            completionType = CompletionType.Single,
            targetFrequency = 1
        )

        bestStreak shouldBe 1
    }

    @Test
    fun `asCurrentStreak should count how many days streak up to today`() {
        val today = GoodHabitTestData.epochDay(7)
        val map = mapOf(
            GoodHabitTestData.epochDay(7) to 1,
            GoodHabitTestData.epochDay(6) to 1,
            GoodHabitTestData.epochDay(5) to 0,
            GoodHabitTestData.epochDay(4) to 1
        )

        val currentStreak = with(GoodHabitDetailLogic) {
            map.countStreakUp(completionType = CompletionType.Single, targetFrequency = 1)
        }

        currentStreak shouldBe 2
    }

    @Test
    fun `calculateMissedCount should return correct number of missed days`() {
        val epoch: (Int) -> Long = { day -> GoodHabitTestData.epochDay(day) }
        val all = listOf(epoch(1), epoch(2), epoch(3), epoch(4), epoch(5))
        val checked = mapOf<Long, Int>(epoch(1) to 1, epoch(3) to 1)
        val startDate = epoch(1)

        val result = calculateMissedCount(
            all,
            checked,
            startDate,
            completionType = CompletionType.Single,
            targetFrequency = 1
        )

        result shouldBe 3
    }

    @Test
    fun `buildGoodHabitModel should return correct result for mixed completion`() {
        val habit = HabitModel(
            id = 1,
            name = "Habit",
            description = "Description",
            startDate = GoodHabitTestData.habitStartDateEpoch,
            completionType = CompletionType.Single,
            targetFrequency = 1
        )

        val checks = listOf(1, 2, 4, 7).mapIndexed { index, day ->
            HabitCheckModel(
                id = index.toLong(),
                date = GoodHabitTestData.epochDay(day),
                isCompleted = true,
                completionCount = 1
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
        result.formattedStartDate shouldBe "01 Jul 2025"
        result.calendarMap.size shouldBe 7
    }
}