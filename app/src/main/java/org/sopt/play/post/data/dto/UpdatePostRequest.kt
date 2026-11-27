package org.sopt.play.post.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostRequest(
    val title: String,
    val content: String
)
