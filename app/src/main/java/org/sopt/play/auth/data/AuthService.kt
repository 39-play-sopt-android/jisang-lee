package org.sopt.play.auth.data

import org.sopt.play.auth.data.dto.AuthorizationCodeRequest
import org.sopt.play.auth.data.dto.AuthorizationCodeResponse
import org.sopt.play.network.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members/authorization-code")
    suspend fun issueAuthorizationCode(
        @Body request: AuthorizationCodeRequest
    ): BaseResponse<AuthorizationCodeResponse>
}
