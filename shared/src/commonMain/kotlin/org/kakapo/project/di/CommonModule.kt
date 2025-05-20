package org.kakapo.project.di

import org.kakapo.project.presentation.pomodoro.PomodoroViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

object CommonModule {

    val viewModel: Module = module {
        viewModel { PomodoroViewModel() }
    }
}

fun initKoin(
    appModule: Module = module { },
    viewModel: Module = CommonModule.viewModel
): KoinApplication = startKoin {
    modules(appModule, viewModel, platformModule)
}