package org.sopt.play.post.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.auth.data.AuthRepository
import org.sopt.play.network.serverMessage
import org.sopt.play.post.data.PostRepository
import org.sopt.play.post.model.Post

class PostViewModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    val keyword = TextFieldState()
    val title = TextFieldState()
    val content = TextFieldState()
    val editTitle = TextFieldState()
    val editContent = TextFieldState()

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadPosts()

        viewModelScope.launch {
            snapshotFlow {
                PostIntent.InputChanged(
                    title = title.text.toString(),
                    content = content.text.toString()
                )
            }.collect { intent ->
                onIntent(intent)
            }
        }

        viewModelScope.launch {
            snapshotFlow {
                PostIntent.EditInputChanged(
                    title = editTitle.text.toString(),
                    content = editContent.text.toString()
                )
            }.collect { intent ->
                onIntent(intent)
            }
        }
    }

    fun onIntent(intent: PostIntent) {
        when (intent) {
            PostIntent.SearchClicked -> onSearch()
            is PostIntent.InputChanged -> onInputChanged(intent.title, intent.content)
            PostIntent.FloatingButtonClicked -> onFloatingButtonClicked()
            PostIntent.SheetDismissed -> onSheetDismissed()
            PostIntent.CreateClicked -> onCreate()
            is PostIntent.PostClicked -> onPostClicked(intent.post)
            is PostIntent.EditInputChanged -> onEditInputChanged(intent.title, intent.content)
            PostIntent.EditSheetDismissed -> onEditSheetDismissed()
            PostIntent.EditClicked -> onEdit()
            is PostIntent.PostLongClicked -> onPostLongClicked(intent.postId)
            PostIntent.DeleteDialogDismissed -> onDeleteDialogDismissed()
            PostIntent.DeleteClicked -> onDelete()
            PostIntent.ErrorMessageShown -> onErrorMessageShown()
            PostIntent.ScrollToTopHandled -> onScrollToTopHandled()
        }
    }

    private fun loadPosts(
        keyword: String = _uiState.value.searchedKeyword,
        shouldScrollToTop: Boolean = false
    ) {
        viewModelScope.launch {
            postRepository.getPosts(keyword = keyword)
                .onSuccess { posts ->
                    _uiState.update { state ->
                        state.copy(
                            posts = posts,
                            searchedKeyword = keyword,
                            shouldScrollToTop = shouldScrollToTop
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update { state ->
                        state.copy(errorMessage = throwable.serverMessage() ?: "게시글을 불러오지 못했어요.")
                    }
                }
        }
    }

    private fun onSearch() {
        loadPosts(
            keyword = keyword.text.toString(),
            shouldScrollToTop = true
        )
    }

    private fun onInputChanged(titleText: String, contentText: String) {
        _uiState.update { state ->
            state.copy(isCreateEnabled = titleText.isNotBlank() && contentText.isNotBlank())
        }
    }

    private fun onFloatingButtonClicked() {
        _uiState.update { state ->
            state.copy(isSheetVisible = true)
        }
    }

    private fun onSheetDismissed() {
        _uiState.update { state ->
            state.copy(isSheetVisible = false)
        }
    }

    private fun onCreate() {
        val author = authRepository.currentUser.value?.name ?: return
        val authorizationCode = authRepository.authorizationCode ?: return

        _uiState.update { state ->
            state.copy(isSheetVisible = false)
        }
        viewModelScope.launch {
            postRepository.createPost(
                authorizationCode = authorizationCode,
                title = title.text.toString(),
                content = content.text.toString(),
                author = author
            )
                .onSuccess {
                    title.clearText()
                    content.clearText()
                    loadPosts(shouldScrollToTop = true)
                }
                .onFailure { throwable ->
                    _uiState.update { state ->
                        state.copy(errorMessage = throwable.serverMessage() ?: "게시글을 작성하지 못했어요.")
                    }
                }
        }
    }

    private fun onPostClicked(post: Post) {
        _uiState.update { state ->
            state.copy(
                editPost = post,
                isEditEnabled = false
            )
        }
        editTitle.setTextAndPlaceCursorAtEnd(post.title)
        editContent.setTextAndPlaceCursorAtEnd(post.content)
    }

    private fun onEditInputChanged(titleText: String, contentText: String) {
        val post = _uiState.value.editPost ?: return

        _uiState.update { state ->
            state.copy(
                isEditEnabled = titleText.isNotBlank() &&
                        contentText.isNotBlank() &&
                        (titleText != post.title || contentText != post.content)
            )
        }
    }

    private fun onEditSheetDismissed() {
        _uiState.update { state ->
            state.copy(editPost = null)
        }
    }

    private fun onEdit() {
        val post = _uiState.value.editPost ?: return
        val authorizationCode = authRepository.authorizationCode ?: return

        _uiState.update { state ->
            state.copy(editPost = null)
        }
        viewModelScope.launch {
            postRepository.updatePost(
                authorizationCode = authorizationCode,
                postId = post.id,
                title = editTitle.text.toString(),
                content = editContent.text.toString()
            )
                .onSuccess {
                    loadPosts()
                }
                .onFailure { throwable ->
                    _uiState.update { state ->
                        state.copy(errorMessage = throwable.serverMessage() ?: "게시글을 수정하지 못했어요.")
                    }
                }
        }
    }

    private fun onPostLongClicked(postId: Long) {
        _uiState.update { state ->
            state.copy(deletePostId = postId)
        }
    }

    private fun onDeleteDialogDismissed() {
        _uiState.update { state ->
            state.copy(deletePostId = null)
        }
    }

    private fun onDelete() {
        val postId = _uiState.value.deletePostId ?: return
        val authorizationCode = authRepository.authorizationCode ?: return

        _uiState.update { state ->
            state.copy(deletePostId = null)
        }
        viewModelScope.launch {
            postRepository.deletePost(
                authorizationCode = authorizationCode,
                postId = postId
            )
                .onSuccess {
                    loadPosts()
                }
                .onFailure { throwable ->
                    _uiState.update { state ->
                        state.copy(errorMessage = throwable.serverMessage() ?: "게시글을 삭제하지 못했어요.")
                    }
                }
        }
    }

    private fun onErrorMessageShown() {
        _uiState.update { state ->
            state.copy(errorMessage = null)
        }
    }

    private fun onScrollToTopHandled() {
        _uiState.update { state ->
            state.copy(shouldScrollToTop = false)
        }
    }
}
