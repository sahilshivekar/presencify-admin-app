package edu.watumull.presencify.core.data.network.teacher_auth

import edu.watumull.presencify.core.data.dto.auth.TokenDto
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints.LOGIN_TEACHER
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints.LOGOUT
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints.REFRESH_TOKENS
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints.SEND_VERIFICATION_CODE
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints.UPDATE_PASSWORD
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints.VERIFY_CODE
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteTeacherAuthDataSource(
    private val httpClient: HttpClient
) : RemoteTeacherAuthDataSource {

    override suspend fun loginTeacher(email: String, password: String): Result<TokenDto, DataError.Remote> {
        return safeCall<TokenDto> {
            httpClient.post(LOGIN_TEACHER) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email, "password" to password))
            }
        }
    }

    override suspend fun sendVerificationCodeToEmail(email: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(SEND_VERIFICATION_CODE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email))
            }
        }
    }

    override suspend fun verifyCode(email: String, code: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(VERIFY_CODE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("email" to email, "code" to code))
            }
        }
    }

    override suspend fun updatePassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.put(UPDATE_PASSWORD) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("password" to password, "confirmPassword" to confirmPassword))
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

    override suspend fun logout(): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(LOGOUT)
        }
    }
}
