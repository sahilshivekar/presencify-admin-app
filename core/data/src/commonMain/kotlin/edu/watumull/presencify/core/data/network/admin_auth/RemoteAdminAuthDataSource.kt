package edu.watumull.presencify.core.data.network.admin_auth

import edu.watumull.presencify.core.data.dto.auth.LoginAdminDto
import edu.watumull.presencify.core.data.dto.auth.SendVerificationCodeDto
import edu.watumull.presencify.core.data.dto.auth.TokenDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteAdminAuthDataSource {
    suspend fun login(emailOrUsername: String, password: String): Result<LoginAdminDto, DataError.Remote>
    suspend fun sendVerificationCodeToEmailForForgotPassword(email: String): Result<SendVerificationCodeDto, DataError.Remote>
    suspend fun verifyCode(email: String, code: String): Result<TokenDto, DataError.Remote>
    suspend fun refreshTokens(refreshToken: String): Result<TokenDto, DataError.Remote>
    suspend fun verifyPassword(password: String): Result<Unit, DataError.Remote>
    suspend fun updateAdminPassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote>
    suspend fun logout(): Result<Unit, DataError.Remote>
    suspend fun sendVerificationCodeToEmail(): Result<SendVerificationCodeDto, DataError.Remote>
}