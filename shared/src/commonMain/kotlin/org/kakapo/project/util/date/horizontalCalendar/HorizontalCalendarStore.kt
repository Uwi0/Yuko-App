package org.kakapo.project.util.date.horizontalCalendar

import co.touchlab.kermit.Logger
import com.kakapo.common.util.asLocalDate
import com.kakapo.common.util.currentDay
import com.kakapo.common.util.currentLocalDate
import com.kakapo.model.date.HorizontalCalendarArgs
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import com.kakapo.model.date.WeekModel
import org.kakapo.project.util.date.util.lastDayOfLastWeekInCurrentMonth
import org.kakapo.project.util.date.util.startOfWeek

class HorizontalCalendarStore {

    var allWeeks: List<WeekModel> = emptyList()
    var currentDate: LocalDate = currentLocalDate
    var onCalendarUpdate: ((HorizontalCalendarArgs) -> Unit)? = null

    private var currentWeek: List<LocalDate> = emptyList()
    private var nextWeek: List<LocalDate> = emptyList()
    private var previousWeek: List<LocalDate> = emptyList()
    private var currentIndex: Int = 0
    private var indexToUpdate: Int = 0

    private var startDateOfWeek: LocalDate = currentLocalDate
    private var lastDateOfWeek: LocalDate = currentLocalDate

    fun initData(startEpochDay: Long, currentEpochDay: Long = currentDay) {
        startDateOfWeek = startOfWeek(startEpochDay.asLocalDate()).plus(1, DateTimeUnit.DAY)
        lastDateOfWeek = lastDayOfLastWeekInCurrentMonth(currentEpochDay.asLocalDate()).plus(1, DateTimeUnit.DAY)

        Logger.d("initData: ${startDateOfWeek.day}: ${startDateOfWeek.dayOfWeek.name}, ${lastDateOfWeek.day}: ${lastDateOfWeek.dayOfWeek.name}")
        fetchCurrentWeek()
        fetchPreviousNextWeek()
        appendAll()
    }

    fun update(index: Int) {
        val value = if (index < currentIndex) {
            1
        } else {
            -1
        }

        val proposedDate = currentDate.plus(value * 7, DateTimeUnit.DAY)

        if (proposedDate < startDateOfWeek || proposedDate > lastDateOfWeek) {
            Logger.d("Blocked scroll: proposedDate=$proposedDate is outside [$startDateOfWeek - $lastDateOfWeek]")
            return
        }

        indexToUpdate = if (value == 1) {
            (indexToUpdate + 1) % 3
        } else {
            (indexToUpdate + 2) % 3
        }

        currentIndex = index
        Logger.d("update: $value, index: $index, indexToUpdate: $indexToUpdate")
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
        val canScrollLeft = currentDate.minus(7, DateTimeUnit.DAY) >= startDateOfWeek
        val canScrollRight = currentDate.plus(7, DateTimeUnit.DAY) <= lastDateOfWeek
        val args = HorizontalCalendarArgs(
            currentDay = currentDate,
            weeks = allWeeks,
            canScrollRight = canScrollRight,
            canScrollLeft = canScrollLeft
        )
        onCalendarUpdate?.invoke(args)
    }

}