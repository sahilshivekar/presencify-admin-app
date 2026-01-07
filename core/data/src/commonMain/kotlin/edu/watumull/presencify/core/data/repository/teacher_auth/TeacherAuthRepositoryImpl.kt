package edu.watumull.presencify.core.data.repository.teacher_auth

import edu.watumull.presencify.core.data.network.teacher_auth.RemoteTeacherAuthDataSource
import edu.watumull.presencify.core.data.repository.auth.TokenRepository
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.teacher_auth.TeacherAuthRepository

class TeacherAuthRepositoryImpl(
    private val remoteDataSource: RemoteTeacherAuthDataSource,
    private val tokenRepository: TokenRepository
) : TeacherAuthRepository {

    override suspend fun loginTeacher(email: String, password: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.loginTeacher(email, password).onSuccess { tokenDto ->
            tokenRepository.saveAccessToken(tokenDto.accessToken)
            tokenRepository.saveRefreshToken(tokenDto.refreshToken)
        }.map {}
    }

    override suspend fun sendVerificationCodeToEmail(email: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.sendVerificationCodeToEmail(email)
    }

    override suspend fun verifyCode(email: String, code: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.verifyCode(email, code)
    }

    override suspend fun updatePassword(password: String, confirmPassword: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.updatePassword(password, confirmPassword)
    }

    override suspend fun refreshTokens(refreshToken: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.refreshTokens(refreshToken).onSuccess { tokenDto ->
            tokenRepository.saveAccessToken(tokenDto.accessToken)
            tokenRepository.saveRefreshToken(tokenDto.refreshToken)
        }.map {}
    }

    override suspend fun logout(): Result<Unit, DataError.Remote> {
        return remoteDataSource.logout()
    }
}
