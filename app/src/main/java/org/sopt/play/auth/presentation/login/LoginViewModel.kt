package org.sopt.play.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.auth.data.AuthRepository
import org.sopt.play.auth.presentation.AuthValidator

class LoginViewModel(
    private val repository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val email = TextFieldState(initialText = savedStateHandle["email"] ?: "")
    val password = TextFieldState()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow {
                LoginIntent.InputChanged(
                    email = email.text.toString(),
                    password = password.text.toString()
                )
            }.collect { intent ->
                onIntent(intent)
            }
        }
    }

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.InputChanged -> onInputChanged(intent.email, intent.password)
            LoginIntent.LoginClicked -> onLogin()
            LoginIntent.ErrorMessageShown -> onErrorMessageShown()
            LoginIntent.LoginHandled -> onLoginHandled()
        }
    }

    private fun onInputChanged(emailText: String, passwordText: String) {
        savedStateHandle["email"] = emailText
        _uiState.update { state ->
            state.copy(
                emailError = AuthValidator.emailError(emailText),
                passwordError = AuthValidator.passwordError(passwordText),
                canLogin = AuthValidator.canLogin(emailText, passwordText)
            )
        }
    }

    private fun onLogin() {
        if (_uiState.value.isLoggedIn) {
            return
        }

        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        if (!AuthValidator.canLogin(emailText, passwordText)) {
            return
        }

        val isLoggedIn = repository.login(emailText, passwordText)
        _uiState.update { state ->
            state.copy(
                isLoggedIn = isLoggedIn,
                errorMessage = if (isLoggedIn) null else "이메일 또는 비밀번호가 올바르지 않아요."
            )
        }
    }

    private fun onErrorMessageShown() {
        _uiState.update { state ->
            state.copy(errorMessage = null)
        }
    }

    private fun onLoginHandled() {
        _uiState.update { state ->
            state.copy(isLoggedIn = false)
        }
    }
}
