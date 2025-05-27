package com.kakapo.preference.datasource.base

interface PreferenceDatasource {
    suspend fun saveLong(key: String, value: Long): Result<Unit>
    suspend fun getLong(key: String): Result<Long>
    suspend fun saveDouble(key: String, value: Double): Result<Unit>
    suspend fun getDouble(key: String): Result<Double>
}