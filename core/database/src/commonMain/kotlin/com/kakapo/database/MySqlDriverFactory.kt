package com.kakapo.database

import app.cash.sqldelight.db.SqlDriver

expect class MySqlDriverFactory {
    fun createDriver(): SqlDriver
}

const val DATABASE_NAME = "yuko.db"