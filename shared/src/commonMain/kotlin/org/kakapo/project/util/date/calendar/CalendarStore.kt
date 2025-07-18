package org.kakapo.project.util.date.calendar

import co.touchlab.kermit.Logger
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import com.kakapo.model.date.DayState
import com.kakapo.model.date.MonthModel
import com.kakapo.model.date.WeekOfMonthModel
import org.kakapo.project.util.date.util.startOfWeek
import kotlin.native.ObjCName
import kotlin.time.Clock

@ObjCName("CalendarStoreKt")
class CalendarStore {

    @NativeCoroutinesState
    val allMonths: StateFlow<List<MonthModel>> get() = _allMonths.asStateFlow()
    private val _allMonths: MutableStateFlow<List<MonthModel>> = MutableStateFlow(emptyList())

    @NativeCoroutinesState
    val currentDate: StateFlow<LocalDate> get() = _currentDate.asStateFlow()
    private val _currentDate = MutableStateFlow(today())

    private var currentIndex: Int = 0
    private var currentMonthOffset: Int = 0
    private val initialDate = today()

    fun initData() {
        currentIndex = 0
        currentMonthOffset = 0
        _currentDate.update { initialDate }

        val current = generateMonth(initialDate)
        val next = generateMonth(initialDate.plus(1, DateTimeUnit.MONTH))
        val previous = generateMonth(initialDate.minus(1, DateTimeUnit.MONTH))

        _allMonths.update { listOf(current, next, previous) }
    }

    fun update(index: Int) {
        val direction = getScrollDirection(currentIndex, index)
        currentIndex = index

        Logger.d("update: currentIndex=$currentIndex, direction=$direction, monthOffset=$currentMonthOffset")

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
        _currentDate.update { newCurrentDate }
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

        val updatedList = _allMonths.value.toMutableList()
        updatedList[index] = newMonth
        _allMonths.update { updatedList }

        Logger.d("updateMonthAt: index=$index, monthOffset=$monthOffset, date=${newMonthDate.month}")
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

    private fun today(): LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    private enum class ScrollDirection {
        FORWARD, BACKWARD, NONE
    }
}