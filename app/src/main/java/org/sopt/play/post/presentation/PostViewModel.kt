package org.sopt.play.post.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.post.data.PostRepository

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            PostIntent.ErrorMessageShown -> onErrorMessageShown()
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            repository.getPosts()
                .onSuccess { posts ->
                    _uiState.update { state ->
                        state.copy(posts = posts)
                    }
                }
                .onFailure {
                    _uiState.update { state ->
                        state.copy(errorMessage = "게시글을 불러오지 못했어요.")
                    }
                }
        }
    }

    private fun onErrorMessageShown() {
        _uiState.update { state ->
            state.copy(errorMessage = null)
        }
    }
}
