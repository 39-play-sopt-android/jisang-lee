package org.sopt.play.post.model

data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdDate: String
)
