package org.sopt.play.auth.presentation.register

data class RegisterUiState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmError: String? = null,
    val isRegisterEnabled: Boolean = false,
    val isRegistered: Boolean = false
)
