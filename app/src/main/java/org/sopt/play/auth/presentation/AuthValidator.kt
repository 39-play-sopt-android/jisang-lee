package org.sopt.play.auth.presentation

import androidx.core.util.PatternsCompat

object AuthValidator {
    private const val MIN_PASSWORD_LENGTH = 6

    fun emailError(email: String): String? {
        return when {
            email.isBlank() || isEmailValid(email) -> null
            else -> "올바른 이메일을 입력해주세요."
        }
    }

    fun passwordError(password: String): String? {
        return when {
            password.isEmpty() || isPasswordValid(password) -> null
            else -> "비밀번호는 6자 이상 입력해주세요."
        }
    }

    fun confirmError(password: String, passwordConfirm: String): String? {
        return when {
            passwordConfirm.isEmpty() || passwordConfirm == password -> null
            else -> "비밀번호가 일치하지 않아요."
        }
    }

    fun canLogin(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun canRegister(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Boolean {
        return name.isNotBlank() &&
                canLogin(email, password) &&
                password == passwordConfirm
    }

    private fun isEmailValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS
            .matcher(email.trim())
            .matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }
}
