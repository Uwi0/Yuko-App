package com.kakapo.common

fun Boolean.asLong(): Long {
    return if (this) 1L else 0L
}