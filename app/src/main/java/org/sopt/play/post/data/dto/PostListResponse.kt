package org.sopt.play.post.data.dto

import kotlinx.serialization.Serializable
import org.sopt.play.post.model.Post

@Serializable
data class PostListResponse(
    val posts: List<PostResponse>
)

@Serializable
data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String
) {
    fun toPost(): Post = Post(
        id = id,
        title = title,
        content = content,
        author = author,
        createdDate = createdAt
            .substringBefore("T")
            .replace("-", ".")
    )
}
