package com.kakapo.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual class YukoPreferenceDataStoreFactory(private val context: Context) {
    actual fun dataStore(): DataStore<Preferences> = createDatabaseStore {
        context.filesDir.resolve(PREFERENCE_NAME).absolutePath
    }
}