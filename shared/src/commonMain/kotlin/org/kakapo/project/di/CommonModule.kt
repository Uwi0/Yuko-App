package org.kakapo.project.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.data.repository.impl.PomodoroSessionRepositoryImpl
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.database.datasource.implementation.PomodoroSessionLocalDatasourceImpl
import com.kakapo.preference.datasource.base.PreferenceDatasource
import com.kakapo.preference.datasource.impl.PreferenceDatasourceImpl
import org.kakapo.project.presentation.notes.NotesViewModel
import org.kakapo.project.presentation.pomodoro.PomodoroViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {

    val viewModel: Module = module {
        viewModel { PomodoroViewModel(get()) }
        viewModel { NotesViewModel() }
    }

    val localDatasourceModule: Module = module {
        factory<PomodoroSessionLocalDatasource> { PomodoroSessionLocalDatasourceImpl(get()) }
    }

    val preferencesModule: Module = module {
        factory<PreferenceDatasource> { PreferenceDatasourceImpl(get<DataStore<Preferences>>()) }
    }

    val repositoryModule: Module = module {
        factory<PomodoroSessionRepository> { PomodoroSessionRepositoryImpl(get(), get()) }
    }
}

fun initKoin(
    appModule: Module = module { },
    viewModel: Module = CommonModule.viewModel,
    localDatasource: Module = CommonModule.localDatasourceModule,
    preference: Module = CommonModule.preferencesModule,
    repository: Module = CommonModule.repositoryModule
): KoinApplication = startKoin {
    modules(appModule, viewModel, localDatasource, preference, repository, platformModule)
}