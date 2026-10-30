package org.sopt.play.auth.presentation

data class LoginUiState(
    val emailError: String? = null,
    val passwordError: String? = null,
    val canLogin: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null
)
