package org.sopt.play.post.presentation

import org.sopt.play.post.model.Post

data class PostUiState(
    val posts: List<Post> = emptyList(),
    val errorMessage: String? = null
)
