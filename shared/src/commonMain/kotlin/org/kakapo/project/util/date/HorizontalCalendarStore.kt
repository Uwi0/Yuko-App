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
        val newList = _allWeeks.value.toMutableList()
        newList.add(WeekModel(0, currentWeek))
        newList.add(WeekModel(2, nextWeek))
        newList.add(WeekModel(1, previousWeek))
        _allWeeks.update { newList }
    }


    fun update(index: Int) {
        var value: Int
        if (index < currentIndex) {
            value = 1
            indexToUpdate = (indexToUpdate + 1) % 3
        } else {
            value = -1
            indexToUpdate = (indexToUpdate + 2) % 3
        }
        currentIndex = index
        addWeek(indexToUpdate, value)
    }

    private fun fetchCurrentWeek() {
        val startOfWeek = startOfWeek(currentDate.value)
        currentWeek = (1..7).map { day ->
            startOfWeek.plus(day, DateTimeUnit.DAY)
        }
    }

    private fun addWeek(index: Int, value: Int) {
        val today = currentDate.value.plus(value * 7, DateTimeUnit.DAY)
        _currentDate.update { today }

        val startOfWeek = startOfWeek(today)
        val weekDays = (1..7).map { day ->
            startOfWeek.plus(day, DateTimeUnit.DAY)
                .also { _currentMonth.update { startOfWeek.plus(day, DateTimeUnit.DAY) }  }
        }

        val newList = _allWeeks.value.toMutableList()
        newList[index] = WeekModel(index, weekDays)
        _allWeeks.update { newList }
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