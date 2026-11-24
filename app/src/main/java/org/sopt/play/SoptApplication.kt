package org.sopt.play

import android.app.Application
import org.sopt.play.auth.data.AuthRepository

class SoptApplication : Application() {
    val authRepository by lazy { AuthRepository() }
}
