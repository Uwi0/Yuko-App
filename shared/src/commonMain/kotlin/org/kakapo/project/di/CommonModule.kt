package org.kakapo.project.di

import com.kakapo.data.repository.base.PomodoroSessionRepository
import com.kakapo.data.repository.impl.PomodoroSessionRepositoryImpl
import com.kakapo.database.datasource.base.PomodoroSessionLocalDatasource
import com.kakapo.database.datasource.implementation.PomodoroSessionLocalDatasourceImpl
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
    }

    val localDatasourceModule: Module = module {
        factory<PomodoroSessionLocalDatasource> { PomodoroSessionLocalDatasourceImpl(get()) }
    }

    val repositoryModule: Module = module {
        factory<PomodoroSessionRepository> { PomodoroSessionRepositoryImpl(get()) }
    }
}

fun initKoin(
    appModule: Module = module { },
    viewModel: Module = CommonModule.viewModel,
    localDatasource: Module = CommonModule.localDatasourceModule,
    repository: Module = CommonModule.repositoryModule
): KoinApplication = startKoin {
    modules(appModule, viewModel, localDatasource, repository, platformModule)
}