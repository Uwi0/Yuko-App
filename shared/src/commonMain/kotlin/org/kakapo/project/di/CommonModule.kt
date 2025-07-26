package org.kakapo.project.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kakapo.data.repository.base.habit.HabitRepository
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.data.repository.base.habit.HabitCheckRepository
import com.kakapo.data.repository.impl.habit.HabitRepositoryImpl
import com.kakapo.data.repository.impl.NotesRepositoryImpl
import com.kakapo.data.repository.impl.PomodoroSessionRepositoryImpl
import com.kakapo.data.repository.impl.TodosRepositoryImpl
import com.kakapo.data.repository.impl.habit.HabitCheckRepositoryImpl
import com.kakapo.database.datasource.base.habits.HabitLocalDatasource
import com.kakapo.database.datasource.base.NotesLocalDatasource
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.database.datasource.base.TodosLocalDatasource
import com.kakapo.database.datasource.base.habits.HabitCheckLocalDatasource
import com.kakapo.database.datasource.implementation.habit.HabitLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.NotesLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.PomodoroSessionLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.TodosLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.habit.HabitCheckLocalDatasourceImpl
import com.kakapo.domain.useCase.base.GoodHabitDetailUseCase
import com.kakapo.domain.useCase.impl.GoodHabitDetailUseCaseImpl
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.impl.PreferenceDatasourceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.kakapo.project.presentation.habitMenu.addHabit.AddHabitViewModel
import org.kakapo.project.presentation.habitMenu.badHabit.BadHabitViewModel
import org.kakapo.project.presentation.habitMenu.goodHabit.GoodHabitViewModel
import org.kakapo.project.presentation.habitMenu.habits.HabitsViewModel
import org.kakapo.project.presentation.noteMenu.addNote.AddNoteViewModel
import org.kakapo.project.presentation.noteMenu.note.NoteViewModel
import org.kakapo.project.presentation.noteMenu.notes.NotesViewModel
import org.kakapo.project.presentation.pomodoroMenu.pomodoro.PomodoroViewModel
import org.kakapo.project.presentation.todoMenu.addTodo.AddTodoViewModel
import org.kakapo.project.presentation.todoMenu.todo.TodoViewModel
import org.kakapo.project.presentation.todoMenu.todos.TodosViewModel
import org.kakapo.project.util.date.horizontalCalendar.HorizontalCalendarStore
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {

    const val IO = "IO"
    const val MAIN = "main"

    val viewModel: Module = module {
        viewModel { PomodoroViewModel(get()) }
        viewModel { NotesViewModel(get()) }
        viewModel { AddNoteViewModel(get()) }
        viewModel { NoteViewModel(get()) }
        viewModel { TodosViewModel(get()) }
        viewModel { AddTodoViewModel(get()) }
        viewModel { TodoViewModel(get()) }
        viewModel { HabitsViewModel(get(), get()) }
        viewModel { AddHabitViewModel(get()) }
        viewModel { BadHabitViewModel(get()) }
        viewModel { GoodHabitViewModel(get(), get()) }
    }

    val localDatasourceModule: Module = module {
        factory<PomodoroSessionLocalDatasource> { PomodoroSessionLocalDatasourceImpl(get()) }
        factory<NotesLocalDatasource> { NotesLocalDatasourceImpl(get(), get(named(IO))) }
        factory<TodosLocalDatasource> { TodosLocalDatasourceImpl(get(), get(named(IO))) }
        factory<HabitLocalDatasource> { HabitLocalDatasourceImpl(get(), get(named(IO))) }
        factory<HabitCheckLocalDatasource> { HabitCheckLocalDatasourceImpl(get(), get(named(IO))) }
    }

    val preferencesModule: Module = module {
        factory<PreferenceDatasource> { PreferenceDatasourceImpl(get<DataStore<Preferences>>()) }
    }
    val repositoryModule: Module = module {
        factory<PomodoroSessionRepository> { PomodoroSessionRepositoryImpl(get(), get()) }
        factory<NotesRepository> { NotesRepositoryImpl(get()) }
        factory<TodosRepository> { TodosRepositoryImpl(get()) }
        factory<HabitRepository> { HabitRepositoryImpl(get()) }
        factory<HabitCheckRepository> { HabitCheckRepositoryImpl(get()) }
    }

    val useCaseModule: Module = module {
        factory<GoodHabitDetailUseCase> { GoodHabitDetailUseCaseImpl(get(), get()) }
    }

    val storeModule: Module = module {
        factory { HorizontalCalendarStore() }
    }

    val coroutineModule: Module = module {
        single<CoroutineDispatcher>(qualifier = named(IO)) { Dispatchers.IO }
        single<CoroutineDispatcher>(qualifier = named(MAIN)) { Dispatchers.Main }
    }
}

fun initKoin(
    appModule: Module = module { },
    viewModel: Module = CommonModule.viewModel,
    localDatasource: Module = CommonModule.localDatasourceModule,
    preference: Module = CommonModule.preferencesModule,
    repository: Module = CommonModule.repositoryModule,
    useCase: Module = CommonModule.useCaseModule,
    storeModule: Module = CommonModule.storeModule,
    coroutineModule: Module = CommonModule.coroutineModule
): KoinApplication = startKoin {
    modules(
        appModule,
        viewModel,
        localDatasource,
        preference,
        repository,
        useCase,
        storeModule,
        coroutineModule,
        platformModule
    )
}
