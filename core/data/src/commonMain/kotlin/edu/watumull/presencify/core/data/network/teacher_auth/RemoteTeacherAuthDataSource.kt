package edu.watumull.presencify.core.data.network.teacher_auth

import edu.watumull.presencify.core.data.dto.auth.TokenDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteTeacherAuthDataSource {
    suspend fun loginTeacher(email: String, password: String): Result<TokenDto, DataError.Remote>

    suspend fun sendVerificationCodeToEmail(email: String): Result<Unit, DataError.Remote>

    suspend fun verifyCode(email: String, code: String): Result<Unit, DataError.Remote>

    suspend fun updatePassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote>

    suspend fun refreshTokens(refreshToken: String): Result<TokenDto, DataError.Remote>

    suspend fun logout(): Result<Unit, DataError.Remote>
}
