package edu.watumull.presencify.core.domain.repository.admin_auth

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.model.auth.LoginAdmin
import edu.watumull.presencify.core.domain.model.auth.SendVerificationCode

interface AdminAuthRepository {
    suspend fun login(emailOrUsername: String, password: String): Result<LoginAdmin, DataError.Remote>
    suspend fun sendVerificationCodeToEmailForForgotPassword(email: String): Result<SendVerificationCode, DataError.Remote>
    suspend fun verifyCode(email: String, code: String): Result<Unit, DataError.Remote>
    suspend fun refreshTokens(refreshToken: String): Result<Unit, DataError.Remote>
    suspend fun verifyPassword(password: String): Result<Unit, DataError.Remote>
    suspend fun updateAdminPassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote>
    suspend fun logout(): Result<Unit, DataError.Remote>
    suspend fun sendVerificationCodeToEmail(): Result<SendVerificationCode, DataError.Remote>
}