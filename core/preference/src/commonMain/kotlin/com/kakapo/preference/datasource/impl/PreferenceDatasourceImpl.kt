package com.kakapo.preference.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.kakapo.preference.datasource.base.PreferenceDatasource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferenceDatasourceImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceDatasource {

    override suspend fun saveLong(
        key: String,
        value: Long
    ): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    override suspend fun getLong(key: String): Result<Long> = runCatching {
        val preferences = dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)] ?: DEFAULT_LONG_VALUE
        }

        preferences.first()
    }

    override suspend fun saveDouble(key: String, value: Double): Result<Unit> = runCatching {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(key)] = value
        }
    }

    override suspend fun getDouble(key: String): Result<Double> = runCatching {
        val preferences = dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: DEFAULT_DOUBLE_VALUE
        }
        preferences.first()
    }

    companion object {
        const val DEFAULT_LONG_VALUE = 0L
        const val DEFAULT_DOUBLE_VALUE = 0.0
    }
}