package org.kakapo.project.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kakapo.data.repository.base.HabitRepository
import com.kakapo.data.repository.base.NotesRepository
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.data.repository.base.TodosRepository
import com.kakapo.data.repository.impl.HabitRepositoryImpl
import com.kakapo.data.repository.impl.NotesRepositoryImpl
import com.kakapo.data.repository.impl.PomodoroSessionRepositoryImpl
import com.kakapo.data.repository.impl.TodosRepositoryImpl
import com.kakapo.database.datasource.base.HabitLocalDatasource
import com.kakapo.database.datasource.base.NotesLocalDatasource
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.database.datasource.base.TodosLocalDatasource
import com.kakapo.database.datasource.implementation.HabitLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.NotesLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.PomodoroSessionLocalDatasourceImpl
import com.kakapo.database.datasource.implementation.TodosLocalDatasourceImpl
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.impl.PreferenceDatasourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.kakapo.project.presentation.habitMenu.addHabit.AddHabitViewModel
import org.kakapo.project.presentation.habitMenu.habits.HabitsViewModel
import org.kakapo.project.presentation.noteMenu.addNote.AddNoteViewModel
import org.kakapo.project.presentation.noteMenu.note.NoteViewModel
import org.kakapo.project.presentation.noteMenu.notes.NotesViewModel
import org.kakapo.project.presentation.pomodoroMenu.pomodoro.PomodoroViewModel
import org.kakapo.project.presentation.todoMenu.addTodo.AddTodoViewModel
import org.kakapo.project.presentation.todoMenu.todo.TodoViewModel
import org.kakapo.project.presentation.todoMenu.todos.TodosViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {

    const val IO = "IO"

    val viewModel: Module = module {
        viewModel { PomodoroViewModel(get()) }
        viewModel { NotesViewModel(get()) }
        viewModel { AddNoteViewModel(get()) }
        viewModel { NoteViewModel(get()) }
        viewModel { TodosViewModel(get()) }
        viewModel { AddTodoViewModel(get()) }
        viewModel { TodoViewModel(get()) }
        viewModel { HabitsViewModel() }
        viewModel { AddHabitViewModel(get()) }
    }

    val localDatasourceModule: Module = module {
        factory<PomodoroSessionLocalDatasource> { PomodoroSessionLocalDatasourceImpl(get()) }
        factory<NotesLocalDatasource> { NotesLocalDatasourceImpl(get(), get(named(IO))) }
        factory<TodosLocalDatasource> { TodosLocalDatasourceImpl(get(), get(named(IO))) }
        factory<HabitLocalDatasource> { HabitLocalDatasourceImpl(get()) }
    }

    val preferencesModule: Module = module {
        factory<PreferenceDatasource> { PreferenceDatasourceImpl(get<DataStore<Preferences>>()) }
    }

    val repositoryModule: Module = module {
        factory<PomodoroSessionRepository> { PomodoroSessionRepositoryImpl(get(), get()) }
        factory<NotesRepository> { NotesRepositoryImpl(get()) }
        factory<TodosRepository> { TodosRepositoryImpl(get()) }
        factory<HabitRepository> { HabitRepositoryImpl(get()) }
    }

    val coroutineModule: Module = module {
        single(qualifier = named(IO)) { Dispatchers.IO }
    }
}

fun initKoin(
    appModule: Module = module { },
    viewModel: Module = CommonModule.viewModel,
    localDatasource: Module = CommonModule.localDatasourceModule,
    preference: Module = CommonModule.preferencesModule,
    repository: Module = CommonModule.repositoryModule,
    coroutineModule: Module = CommonModule.coroutineModule
): KoinApplication = startKoin {
    modules(
        appModule,
        viewModel,
        localDatasource,
        preference,
        repository,
        coroutineModule,
        platformModule
    )
}
