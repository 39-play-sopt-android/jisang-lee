package org.sopt.play.auth.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.play.auth.data.dto.AuthorizationCodeRequest
import org.sopt.play.auth.model.User

class AuthRepository(
    private val service: AuthService
) {
    private var registeredUser: User? = null
    private var registeredPassword: String? = null

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    var authorizationCode: String? = null
        private set

    fun register(name: String, email: String, password: String) {
        registeredUser = User(
            name = name.trim(),
            email = email.trim()
        )
        registeredPassword = password
    }

    suspend fun login(email: String, password: String): Result<Boolean> = runCatching {
        val user = registeredUser ?: return@runCatching false
        if (email.trim() != user.email || password != registeredPassword) {
            return@runCatching false
        }

        authorizationCode = service.issueAuthorizationCode(
            AuthorizationCodeRequest(part = "ANDROID")
        ).data.authorizationCode
        _currentUser.value = user
        registeredUser = null
        registeredPassword = null
        true
    }
}
