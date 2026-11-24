package org.sopt.play.auth.presentation.register

sealed interface RegisterIntent {
    data class InputChanged(
        val name: String,
        val email: String,
        val password: String,
        val passwordConfirm: String
    ) : RegisterIntent

    data object RegisterClicked : RegisterIntent
    data object RegisterHandled : RegisterIntent
}
