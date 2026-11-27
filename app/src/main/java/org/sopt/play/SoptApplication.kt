package org.sopt.play

import android.app.Application
import org.sopt.play.auth.data.AuthRepository
import org.sopt.play.network.ApiFactory
import org.sopt.play.post.data.PostRepository

class SoptApplication : Application() {
    val authRepository by lazy { AuthRepository() }
    val postRepository by lazy { PostRepository(ApiFactory.postService) }
}
