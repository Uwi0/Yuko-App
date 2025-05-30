package com.kakapo.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual class YukoPreferenceDataStoreFactory {

    @OptIn(ExperimentalForeignApi::class)
    actual fun dataStore(): DataStore<Preferences> = createDatabaseStore {
        val documentDirectory: NSURL? = NSFileManager.defaultManager().URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(documentDirectory).path + "/$PREFERENCE_NAME"
    }
}
