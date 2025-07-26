package org.kakapo.project.presentation.habitMenu.model

enum class CompletionViewMode {
    WEEKLY, MONTHLY, YEARLY
}

fun CompletionViewMode.nextMode(): CompletionViewMode {
    return when(this) {
        CompletionViewMode.WEEKLY -> CompletionViewMode.MONTHLY
        CompletionViewMode.MONTHLY -> CompletionViewMode.YEARLY
        CompletionViewMode.YEARLY -> CompletionViewMode.WEEKLY
    }
}