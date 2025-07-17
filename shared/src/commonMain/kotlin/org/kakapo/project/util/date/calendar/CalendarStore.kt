package org.kakapo.project.util.date.calendar

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
import org.kakapo.project.util.date.model.DayState
import org.kakapo.project.util.date.model.MonthModel
import org.kakapo.project.util.date.model.WeekModel
import org.kakapo.project.util.date.model.WeekOfMonthModel
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
    private var indexToUpdate: Int = 0

    fun initData() {
        val current = generateMonth(currentDate.value)
        val previous = generateMonth(currentDate.value.minus(1, DateTimeUnit.MONTH))
        val next = generateMonth(currentDate.value.plus(1, DateTimeUnit.MONTH))

        _allMonths.update { listOf(current, next, previous) }
    }

    fun update(index: Int) {
        val value = if (index < currentIndex) {
            indexToUpdate = if (indexToUpdate == 2) 0 else indexToUpdate + 1
            1
        } else {
            indexToUpdate = if (indexToUpdate == 0) 2 else indexToUpdate - 1
            -1
        }
        currentIndex = index
        addMonth(indexToUpdate, value)
    }

    private fun addMonth(index: Int, value: Int) {
        val newMonthDate = currentDate.value.plus(value, DateTimeUnit.MONTH)
        _currentDate.update { newMonthDate }

        val newMonth = generateMonth(newMonthDate)

        val updatedList = _allMonths.value.toMutableList()
        updatedList[index] = newMonth
        _allMonths.update { updatedList }

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
}