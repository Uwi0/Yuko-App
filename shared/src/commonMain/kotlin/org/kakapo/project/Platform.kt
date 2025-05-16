package org.kakapo.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform