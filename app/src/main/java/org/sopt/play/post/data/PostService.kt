package org.sopt.play.post.data

import org.sopt.play.network.BaseResponse
import org.sopt.play.post.data.dto.PostListResponse
import retrofit2.http.GET

interface PostService {
    @GET("api/v1/posts")
    suspend fun getPosts(): BaseResponse<PostListResponse>
}
