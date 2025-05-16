package org.kakapo.project.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

fun initKoin(
    appModule: Module = module {  },
): KoinApplication = startKoin {
    modules(platformModule)
}