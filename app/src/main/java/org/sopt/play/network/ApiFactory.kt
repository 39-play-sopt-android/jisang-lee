package org.sopt.play.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.play.BuildConfig
import org.sopt.play.auth.data.AuthService
import org.sopt.play.post.data.PostService
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object ApiFactory {
    val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val authService: AuthService = retrofit.create()
    val postService: PostService = retrofit.create()
}
