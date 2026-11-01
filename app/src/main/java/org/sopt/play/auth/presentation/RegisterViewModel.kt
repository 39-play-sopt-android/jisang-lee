package org.sopt.play.auth.presentation

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

class RegisterViewModel(
    private val repository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val name = TextFieldState(initialText = savedStateHandle["name"] ?: "")
    val email = TextFieldState(initialText = savedStateHandle["email"] ?: "")
    val password = TextFieldState()
    val passwordConfirm = TextFieldState()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow {
                RegisterIntent.InputChanged(
                    name = name.text.toString(),
                    email = email.text.toString(),
                    password = password.text.toString(),
                    passwordConfirm = passwordConfirm.text.toString()
                )
            }.collect { intent ->
                onIntent(intent)
            }
        }
    }

    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.InputChanged -> onInputChanged(
                nameText = intent.name,
                emailText = intent.email,
                passwordText = intent.password,
                confirmText = intent.passwordConfirm
            )
            RegisterIntent.RegisterClicked -> onRegister()
            RegisterIntent.RegisterHandled -> onRegisterHandled()
        }
    }

    private fun onInputChanged(
        nameText: String,
        emailText: String,
        passwordText: String,
        confirmText: String
    ) {
        savedStateHandle["name"] = nameText
        savedStateHandle["email"] = emailText
        _uiState.update { state ->
            state.copy(
                emailError = AuthValidator.emailError(emailText),
                passwordError = AuthValidator.passwordError(passwordText),
                confirmError = AuthValidator.confirmError(passwordText, confirmText),
                canRegister = AuthValidator.canRegister(
                    name = nameText,
                    email = emailText,
                    password = passwordText,
                    passwordConfirm = confirmText
                )
            )
        }
    }

    private fun onRegister() {
        if (_uiState.value.isRegistered) {
            return
        }

        val nameText = name.text.toString()
        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        val confirmText = passwordConfirm.text.toString()
        val canRegister = AuthValidator.canRegister(
            name = nameText,
            email = emailText,
            password = passwordText,
            passwordConfirm = confirmText
        )
        if (!canRegister) {
            return
        }

        repository.register(
            name = nameText,
            email = emailText,
            password = passwordText
        )
        _uiState.update { state ->
            state.copy(isRegistered = true)
        }
    }

    private fun onRegisterHandled() {
        _uiState.update { state ->
            state.copy(isRegistered = false)
        }
    }
}
