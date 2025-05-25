package org.kakapo.project.di

import com.kakapo.database.MySqlDriverFactory
import com.kakapo.preference.YukoPreferenceDataStoreFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { MySqlDriverFactory(get()).createDriver() }
    single { YukoPreferenceDataStoreFactory(get()).dataStore() }
}