package edu.watumull.presencify.core.domain.repository.student_auth

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface StudentAuthRepository {
    suspend fun loginStudent(emailOrPRN: String, password: String): Result<Unit, DataError.Remote>

    suspend fun sendVerificationCodeToEmail(email: String): Result<Unit, DataError.Remote>

    suspend fun verifyCode(email: String, code: String): Result<Unit, DataError.Remote>

    suspend fun updatePassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote>

    suspend fun refreshTokens(refreshToken: String): Result<Unit, DataError.Remote>

    suspend fun logout(): Result<Unit, DataError.Remote>
}
