package org.sopt.play.auth.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
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
fun LoginRoute(
    repository: AuthRepository,
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    val viewModel: LoginViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    repository = repository,
                    savedStateHandle = createSavedStateHandle()
                )
            }
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnLogin by rememberUpdatedState(onLogin)

    LaunchedEffect(viewModel, lifecycleOwner, context) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state ->
                if (state.isLoggedIn) {
                    currentOnLogin()
                    viewModel.onLoginHandled()
                }

                state.errorMessage?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    viewModel.onErrorMessageShown()
                }
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        email = viewModel.email,
        password = viewModel.password,
        onLogin = { viewModel.onLogin() },
        onSignUp = onSignUp
    )
}
