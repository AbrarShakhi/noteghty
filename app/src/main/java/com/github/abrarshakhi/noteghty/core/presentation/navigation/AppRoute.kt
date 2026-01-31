package com.github.abrarshakhi.noteghty.core.presentation.navigation

abstract class AppRoute(val pattern: String) {
    open fun route(): String = pattern
}
