package edu.watumull.presencify.core.data.network.student_auth

import edu.watumull.presencify.core.data.dto.auth.TokenDto
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints.LOGIN_STUDENT
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints.LOGOUT
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints.REFRESH_TOKENS
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints.SEND_VERIFICATION_CODE
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints.UPDATE_PASSWORD
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints.VERIFY_CODE
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorRemoteStudentAuthDataSource(
    private val httpClient: HttpClient
) : RemoteStudentAuthDataSource {

    override suspend fun loginStudent(emailOrPRN: String, password: String): Result<TokenDto, DataError.Remote> {
        return safeCall<TokenDto> {
            httpClient.post(LOGIN_STUDENT) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("emailOrPRN" to emailOrPRN, "password" to password))
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
