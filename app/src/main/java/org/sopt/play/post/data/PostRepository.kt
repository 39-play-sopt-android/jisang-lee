package org.sopt.play.post.data

import org.sopt.play.post.model.Post

class PostRepository(
    private val service: PostService
) {
    suspend fun getPosts(): Result<List<Post>> = runCatching {
        service.getPosts().data.posts.map { post -> post.toPost() }
    }
}
