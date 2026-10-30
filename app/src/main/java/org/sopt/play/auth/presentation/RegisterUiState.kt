package org.sopt.play.auth.presentation

data class RegisterUiState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmError: String? = null,
    val canRegister: Boolean = false,
    val isRegistered: Boolean = false
)
