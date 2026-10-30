package org.sopt.play.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.sopt.play.auth.data.AuthRepository

@Composable
fun RegisterRoute(
    repository: AuthRepository,
    onRegister: () -> Unit
) {
    val viewModel: RegisterViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                RegisterViewModel(
                    repository = repository,
                    savedStateHandle = createSavedStateHandle()
                )
            }
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnRegister by rememberUpdatedState(onRegister)

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state ->
                if (state.isRegistered) {
                    currentOnRegister()
                    viewModel.onRegisterHandled()
                }
            }
        }
    }

    RegisterScreen(
        uiState = uiState,
        name = viewModel.name,
        email = viewModel.email,
        password = viewModel.password,
        passwordConfirm = viewModel.passwordConfirm,
        onRegister = { viewModel.onRegister() }
    )
}
