package com.kakapo.model.habit

enum class CompletionType {
    Single, Frequency
}

fun String.toCompletionType(): CompletionType {
    return when (this) {
        CompletionType.Single.name -> CompletionType.Single
        CompletionType.Frequency.name -> CompletionType.Frequency
        else -> throw IllegalArgumentException("Invalid CompletionType value: $this")
    }
}