package org.sopt.play.post.presentation

import org.sopt.play.post.model.Post

data class PostUiState(
    val posts: List<Post> = emptyList(),
    val isSheetVisible: Boolean = false,
    val isCreateEnabled: Boolean = false,
    val isPostCreated: Boolean = false,
    val deletePostId: Long? = null,
    val errorMessage: String? = null
)
