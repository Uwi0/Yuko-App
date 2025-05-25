package com.kakapo.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDatabaseStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )
}

internal const val PREFERENCE_NAME = "yuko_preferences"

expect class YukoPreferenceDataStoreFactory {
    fun dataStore(): DataStore<Preferences>
}