package org.kakapo.project.util.date.calendar

import com.kakapo.common.util.asLocalDate
import com.kakapo.common.util.currentLocalDate
import com.kakapo.common.util.startOfWeek
import com.kakapo.model.date.CalendarArgs
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import com.kakapo.model.date.DayState
import com.kakapo.model.date.MonthModel
import com.kakapo.model.date.WeekOfMonthModel

class CalendarStore {

    var allMonths: List<MonthModel> = emptyList()
    var currentDate = currentLocalDate
    var onCalendarUpdate: ((CalendarArgs) -> Unit)? = null

    private var startDateOfMonth: LocalDate = currentLocalDate
    private var endDateOfMonth: LocalDate = currentLocalDate
    private var currentIndex: Int = 0
    private var currentMonthOffset: Int = 0
    private val initialDate = currentLocalDate

    fun initData(startEpochDay: Long, currentEpochDay: Long) {
        val current = generateMonth(initialDate)
        val next = generateMonth(initialDate.plus(1, DateTimeUnit.MONTH))
        val previous = generateMonth(initialDate.minus(1, DateTimeUnit.MONTH))

        startDateOfMonth = startEpochDay.asLocalDate()
        endDateOfMonth = currentEpochDay.asLocalDate()

        allMonths = listOf(current, next, previous)
        notifyChanged()
    }

    fun update(index: Int) {
        val direction = getScrollDirection(currentIndex, index)
        currentIndex = index

        when (direction) {
            ScrollDirection.FORWARD -> {
                currentMonthOffset++
                updateMonthAt(getPreviousIndex(index), currentMonthOffset + 1)
            }

            ScrollDirection.BACKWARD -> {
                currentMonthOffset--
                updateMonthAt(getNextIndex(index), currentMonthOffset - 1)
            }

            ScrollDirection.NONE -> {
                return
            }
        }

        val newCurrentDate = initialDate.plus(currentMonthOffset, DateTimeUnit.MONTH)
        currentDate = newCurrentDate
        notifyChanged()
    }

    private fun getScrollDirection(from: Int, to: Int): ScrollDirection {
        return when {
            from == to -> ScrollDirection.NONE
            (from == 0 && to == 1) || (from == 1 && to == 2) || (from == 2 && to == 0) -> ScrollDirection.BACKWARD
            (from == 1 && to == 0) || (from == 2 && to == 1) || (from == 0 && to == 2) -> ScrollDirection.FORWARD
            else -> ScrollDirection.NONE
        }
    }

    private fun getPreviousIndex(currentIndex: Int): Int {
        return when (currentIndex) {
            0 -> 2
            1 -> 0
            2 -> 1
            else -> 0
        }
    }

    private fun getNextIndex(currentIndex: Int): Int {
        return when (currentIndex) {
            0 -> 1
            1 -> 2
            2 -> 0
            else -> 0
        }
    }

    private fun updateMonthAt(index: Int, monthOffset: Int) {
        val newMonthDate = initialDate.plus(monthOffset, DateTimeUnit.MONTH)
        val newMonth = generateMonth(newMonthDate)

        val updatedList = allMonths.toMutableList()
        updatedList[index] = newMonth
        allMonths = updatedList

    }

    private fun generateMonth(baseDate: LocalDate): MonthModel {
        val start = LocalDate(baseDate.year, baseDate.month.number, 1)
        val weeks = mutableListOf<WeekOfMonthModel>()
        var weekStart = startOfWeek(start)
        var weekId = 0

        repeat(6) {
            val weekDays = (0..6).map { day ->
                val currentDate = weekStart.plus(day, DateTimeUnit.DAY)
                if (currentDate.month.number == baseDate.month.number) {
                    DayState.Day(currentDate)
                } else {
                    DayState.Empty
                }
            }
            weeks.add(WeekOfMonthModel(weekId++, weekDays))
            weekStart = weekStart.plus(7, DateTimeUnit.DAY)
        }

        return MonthModel(id = baseDate.month.number, weeks = weeks)
    }

    private fun notifyChanged() {
        val canScrollLeft = currentDate > startDateOfMonth
        val canScrollRight = currentDate < endDateOfMonth

        val calendarArgs = CalendarArgs(
            currentDate = currentDate,
            months = allMonths,
            canScrollRight = canScrollRight,
            canScrollLeft = canScrollLeft
        )
        onCalendarUpdate?.invoke(calendarArgs)
    }

    private enum class ScrollDirection {
        FORWARD, BACKWARD, NONE
    }
}