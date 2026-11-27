package org.sopt.play.post.data

import org.sopt.play.network.BaseResponse
import org.sopt.play.post.data.dto.CreatePostRequest
import org.sopt.play.post.data.dto.PostListResponse
import org.sopt.play.post.data.dto.PostResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @GET("api/v1/posts")
    suspend fun getPosts(): BaseResponse<PostListResponse>

    @POST("api/v1/posts")
    suspend fun createPost(
        @Header("Authorization") authorizationCode: String,
        @Body request: CreatePostRequest
    ): BaseResponse<PostResponse>

    @DELETE("api/v1/posts/{postId}")
    suspend fun deletePost(
        @Header("Authorization") authorizationCode: String,
        @Path("postId") postId: Long
    )
}
