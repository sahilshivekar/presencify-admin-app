package edu.watumull.presencify.core.data.network.admin_auth

import edu.watumull.presencify.core.data.dto.auth.LoginAdminDto
import edu.watumull.presencify.core.data.dto.auth.SendVerificationCodeDto
import edu.watumull.presencify.core.data.dto.auth.TokenDto
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.LOGIN
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.LOGOUT
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.REFRESH_TOKENS
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.SEND_VERIFICATION_CODE_EMAIL
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.SEND_VERIFICATION_CODE_FORGOT
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.UPDATE_ADMIN_PASSWORD
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.VERIFY_CODE
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints.VERIFY_PASSWORD
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteAdminAuthDataSource(
    private val httpClient: HttpClient
) : RemoteAdminAuthDataSource {

    override suspend fun login(emailOrUsername: String, password: String): Result<LoginAdminDto, DataError.Remote> {
        return safeCall<LoginAdminDto> {
            httpClient.post(LOGIN) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("emailOrUsername" to emailOrUsername, "password" to password))
            }
        }
    }

    override suspend fun sendVerificationCodeToEmailForForgotPassword(email: String): Result<SendVerificationCodeDto, DataError.Remote> {
        return safeCall<SendVerificationCodeDto> {
            httpClient.post(SEND_VERIFICATION_CODE_FORGOT) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email))
            }
        }
    }

    override suspend fun verifyCode(email: String, code: String): Result<TokenDto, DataError.Remote> {
        return safeCall<TokenDto> {
            httpClient.post(VERIFY_CODE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email, "code" to code))
            }
        }
    }

    override suspend fun refreshTokens(refreshToken: String): Result<TokenDto, DataError.Remote> {
        return safeCall<TokenDto> {
            httpClient.post(REFRESH_TOKENS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("refreshToken" to refreshToken))
            }
        }
    }

    override suspend fun verifyPassword(password: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(VERIFY_PASSWORD) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("password" to password))
            }
        }
    }

    override suspend fun updateAdminPassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.put(UPDATE_ADMIN_PASSWORD) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("password" to password, "confirmPassword" to confirmPassword))
            }
        }
    }

    override suspend fun logout(): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(LOGOUT)
        }
    }

    override suspend fun sendVerificationCodeToEmail(): Result<SendVerificationCodeDto, DataError.Remote> {
        return safeCall<SendVerificationCodeDto> {
            httpClient.get(SEND_VERIFICATION_CODE_EMAIL)
        }
    }
}