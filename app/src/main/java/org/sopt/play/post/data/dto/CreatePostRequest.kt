package org.sopt.play.post.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val title: String,
    val content: String,
    val author: String
)
