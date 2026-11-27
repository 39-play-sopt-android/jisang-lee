package org.sopt.play.post.data

import org.sopt.play.post.data.dto.CreatePostRequest
import org.sopt.play.post.model.Post

class PostRepository(
    private val service: PostService
) {
    suspend fun getPosts(): Result<List<Post>> = runCatching {
        service.getPosts().data.posts.map { post -> post.toPost() }
    }

    suspend fun createPost(
        authorizationCode: String,
        title: String,
        content: String,
        author: String
    ): Result<Unit> = runCatching {
        service.createPost(
            authorizationCode = authorizationCode,
            request = CreatePostRequest(
                title = title,
                content = content,
                author = author
            )
        )
    }

    suspend fun deletePost(
        authorizationCode: String,
        postId: Long
    ): Result<Unit> = runCatching {
        service.deletePost(
            authorizationCode = authorizationCode,
            postId = postId
        )
    }
}
