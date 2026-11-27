package org.sopt.play.network

import kotlinx.serialization.Serializable
import retrofit2.HttpException

@Serializable
data class ErrorResponse(
    val message: String
)

fun Throwable.serverMessage(): String? {
    val errorBody = (this as? HttpException)?.response()?.errorBody()?.string() ?: return null

    return runCatching {
        ApiFactory.json.decodeFromString<ErrorResponse>(errorBody).message
    }.getOrNull()
}
