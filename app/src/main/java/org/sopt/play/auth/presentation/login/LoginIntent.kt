package org.sopt.play.auth.presentation.login

sealed interface LoginIntent {
    data class InputChanged(val email: String, val password: String) : LoginIntent
    data object LoginClicked : LoginIntent
    data object ErrorMessageShown : LoginIntent
    data object LoginHandled : LoginIntent
}
