package org.sopt.play.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationCodeResponse(
    val authorizationCode: String
)
