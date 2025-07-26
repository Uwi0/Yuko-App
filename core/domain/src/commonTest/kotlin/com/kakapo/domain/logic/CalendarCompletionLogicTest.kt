package com.kakapo.domain.logic

import com.kakapo.domain.model.completion
import com.kakapo.domain.model.weekOfMonthModel
import com.kakapo.domain.useCase.logic.asCompletionValue
import com.kakapo.model.date.DayState
import com.kakapo.model.date.DayValue
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CalendarCompletionLogicTest {

    @Test
    fun `test function asCompletion value should return 3 day value`() {
        val result = weekOfMonthModel.days.filterIsInstance<DayState.Day>().map {
            it.asCompletionValue(completion)
        }

        val correctValue = result.filterIsInstance<DayValue.Day>()
        correctValue.size shouldBe 3
    }
}