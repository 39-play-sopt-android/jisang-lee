package org.sopt.play.post.presentation

import android.widget.Toast
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import org.sopt.play.auth.data.AuthRepository
import org.sopt.play.post.data.PostRepository

@Composable
fun PostRoute(
    postRepository: PostRepository,
    authRepository: AuthRepository
) {
    val viewModel: PostViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                PostViewModel(
                    postRepository = postRepository,
                    authRepository = authRepository
                )
            }
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel, lifecycleOwner, context) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect { state ->
                state.errorMessage?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    viewModel.onIntent(PostIntent.ErrorMessageShown)
                }
            }
        }
    }

    LaunchedEffect(uiState.isPostCreated) {
        if (uiState.isPostCreated) {
            listState.scrollToItem(index = 0)
            viewModel.onIntent(PostIntent.PostCreatedHandled)
        }
    }

    PostScreen(
        uiState = uiState,
        listState = listState,
        title = viewModel.title,
        content = viewModel.content,
        onFloatingButtonClick = { viewModel.onIntent(PostIntent.FloatingButtonClicked) },
        onSheetDismiss = { viewModel.onIntent(PostIntent.SheetDismissed) },
        onCreateClick = { viewModel.onIntent(PostIntent.CreateClicked) }
    )
}
