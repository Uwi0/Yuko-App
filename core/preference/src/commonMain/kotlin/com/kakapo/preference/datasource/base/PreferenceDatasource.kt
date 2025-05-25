package com.kakapo.preference.datasource.base

interface PreferenceDatasource {
    suspend fun saveLong(key: String, value: Long): Result<Unit>
    suspend fun getLong(key: String): Result<Long>
}