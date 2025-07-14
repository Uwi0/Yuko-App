package org.kakapo.project.util.date

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.kakapo.project.util.date.model.WeekModel
import kotlin.native.ObjCName
import kotlin.time.Clock

@ObjCName("HorizontalCalendarStoreKt")
class HorizontalCalendarStore {

    @NativeCoroutinesState
    val allWeeks: StateFlow<List<WeekModel>> get() = _allWeeks.asStateFlow()
    private val _allWeeks: MutableStateFlow<List<WeekModel>> = MutableStateFlow(emptyList())

    @NativeCoroutinesState
    val currentDate: StateFlow<LocalDate> get() = _currentDate.asStateFlow()
    private val _currentDate: MutableStateFlow<LocalDate> = MutableStateFlow(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )

    @NativeCoroutinesState
    val currentMonth: StateFlow<LocalDate> get() = _currentMonth.asStateFlow()
    private val _currentMonth: MutableStateFlow<LocalDate> = MutableStateFlow(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )

    private var currentWeek: List<LocalDate> = emptyList()
    private var nextWeek: List<LocalDate> = emptyList()
    private var previousWeek: List<LocalDate> = emptyList()
    private var currentIndex: Int = 0
    private var indexToUpdate: Int = 0

    fun initData() {
        fetchCurrentWeek()
        fetchPreviousNextWeek()
        appendAll()
    }

    private fun appendAll() {
        val weeks = mutableListOf<WeekModel>()
        weeks.add(WeekModel(0, currentWeek))
        weeks.add(WeekModel(2, nextWeek))
        weeks.add(WeekModel(1, previousWeek))
        _allWeeks.update {  weeks }
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
        addWeek(indexToUpdate, value)
    }

    private fun addWeek(index: Int, value: Int) {
        val today = currentDate.value.plus(value * 7, DateTimeUnit.DAY)
        _currentDate.update { today }

        val startOfWeek = startOfWeek(today)
        val weekDays = mutableListOf<LocalDate>()

        for (day in 1..7) {
            val weekday = startOfWeek.plus(day, DateTimeUnit.DAY)
            weekDays.add(weekday)
            _currentMonth.update { weekday }
        }

        val newList = _allWeeks.value.toMutableList()
        val oldWeek = newList[index]
        newList[index] = WeekModel(oldWeek.id, weekDays)
        _allWeeks.update {  newList }
    }

    private fun fetchCurrentWeek() {
        val startOfWeek = startOfWeek(currentDate.value)
        currentWeek = (1..7).map { day ->
            startOfWeek.plus(day, DateTimeUnit.DAY)
        }
    }

    private fun fetchPreviousNextWeek() {
        val nextStart = startOfWeek(currentDate.value.plus(7, DateTimeUnit.DAY))
        nextWeek = (1..7).map { day ->
            nextStart.plus(day, DateTimeUnit.DAY)
        }

        val prevStart = startOfWeek(currentDate.value.minus(7, DateTimeUnit.DAY))
        previousWeek = (1..7).map { day ->
            prevStart.plus(day, DateTimeUnit.DAY)
        }
    }

    private fun startOfWeek(date: LocalDate): LocalDate {
        val dow = date.dayOfWeek.isoDayNumber
        return date.minus((dow % 7).toLong(), DateTimeUnit.DAY)
    }

}