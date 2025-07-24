package org.kakapo.project.util.date.horizontalCalendar

import com.kakapo.common.util.currentLocalDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import com.kakapo.model.date.WeekModel
import org.kakapo.project.util.date.util.startOfWeek
import kotlin.native.ObjCName

class HorizontalCalendarStore {

    var allWeeks: List<WeekModel> = emptyList()
    var currentDate: LocalDate = currentLocalDate
    var onCalendarUpdate: ((List<WeekModel>, LocalDate) -> Unit)? = null

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

    private fun appendAll() {
        val weeks = mutableListOf<WeekModel>()
        weeks.add(WeekModel(0, currentWeek))
        weeks.add(WeekModel(2, nextWeek))
        weeks.add(WeekModel(1, previousWeek))
        allWeeks = weeks
        notifyChange()
    }

    private fun addWeek(index: Int, value: Int) {
        val today = currentDate.plus(value * 7, DateTimeUnit.DAY)
        currentDate = today

        val startOfWeek = startOfWeek(today)
        val weekDays = mutableListOf<LocalDate>()

        for (day in 1..7) {
            val weekday = startOfWeek.plus(day, DateTimeUnit.DAY)
            weekDays.add(weekday)
        }

        val newWeek = allWeeks.toMutableList()
        val oldWeek = newWeek[index]
        newWeek[index] = WeekModel(oldWeek.id, weekDays)
        allWeeks = newWeek
        notifyChange()
    }

    private fun fetchCurrentWeek() {
        val startOfWeek = startOfWeek(currentDate)
        currentWeek = (1..7).map { day ->
            startOfWeek.plus(day, DateTimeUnit.DAY)
        }
    }

    private fun fetchPreviousNextWeek() {
        val nextStart = startOfWeek(currentDate.plus(7, DateTimeUnit.DAY))
        nextWeek = (1..7).map { day ->
            nextStart.plus(day, DateTimeUnit.DAY)
        }

        val prevStart = startOfWeek(currentDate.minus(7, DateTimeUnit.DAY))
        previousWeek = (1..7).map { day ->
            prevStart.plus(day, DateTimeUnit.DAY)
        }
    }

    private fun notifyChange() {
        onCalendarUpdate?.invoke(allWeeks, currentDate)
    }

}