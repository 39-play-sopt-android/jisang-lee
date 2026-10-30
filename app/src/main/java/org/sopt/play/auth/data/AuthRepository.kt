package org.sopt.play.auth.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.play.auth.model.User

class AuthRepository {
    private var registeredUser: User? = null
    private var registeredPassword: String? = null

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun register(name: String, email: String, password: String) {
        registeredUser = User(
            name = name.trim(),
            email = email.trim()
        )
        registeredPassword = password
    }

    fun login(email: String, password: String): Boolean {
        val user = registeredUser ?: return false
        if (email.trim() != user.email || password != registeredPassword) {
            return false
        }

        _currentUser.value = user
        registeredUser = null
        registeredPassword = null
        return true
    }
}
