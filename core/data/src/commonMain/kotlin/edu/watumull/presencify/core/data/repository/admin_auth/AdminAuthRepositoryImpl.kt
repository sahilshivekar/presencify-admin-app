package edu.watumull.presencify.core.data.repository.admin_auth

import edu.watumull.presencify.core.data.mapper.auth.toDomain
import edu.watumull.presencify.core.data.network.admin_auth.RemoteAdminAuthDataSource
import edu.watumull.presencify.core.data.repository.auth.RoleRepository
import edu.watumull.presencify.core.data.repository.auth.TokenRepository
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.auth.LoginAdmin
import edu.watumull.presencify.core.domain.model.auth.SendVerificationCode
import edu.watumull.presencify.core.domain.model.auth.UserRole
import edu.watumull.presencify.core.domain.onSuccess
import edu.watumull.presencify.core.domain.repository.admin_auth.AdminAuthRepository

class AdminAuthRepositoryImpl(
    private val remoteDataSource: RemoteAdminAuthDataSource,
    private val tokenRepository: TokenRepository,
    private val roleRepository: RoleRepository,
) : AdminAuthRepository {

    override suspend fun login(
        emailOrUsername: String,
        password: String,
    ): Result<LoginAdmin, DataError.Remote> {
        return remoteDataSource.login(emailOrUsername, password).onSuccess { tokenDto ->
            tokenRepository.saveAccessToken(tokenDto.accessToken)
            tokenRepository.saveRefreshToken(tokenDto.refreshToken)
            roleRepository.saveUserRole(UserRole.ADMIN)
        }.map { it.toDomain() }
    }

    override suspend fun sendVerificationCodeToEmailForForgotPassword(email: String): Result<SendVerificationCode, DataError.Remote> {
        return remoteDataSource.sendVerificationCodeToEmailForForgotPassword(email)
            .map { it.toDomain() }
    }

    override suspend fun verifyCode(email: String, code: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.verifyCode(email, code).onSuccess { tokenDto ->
            tokenRepository.saveAccessToken(tokenDto.accessToken)
            tokenRepository.saveRefreshToken(tokenDto.refreshToken)
            roleRepository.saveUserRole(UserRole.ADMIN)
        }.map {}
    }

    override suspend fun refreshTokens(refreshToken: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.refreshTokens(refreshToken).onSuccess { tokenDto ->
            tokenRepository.saveAccessToken(tokenDto.accessToken)
            tokenRepository.saveRefreshToken(tokenDto.refreshToken)
        }.map {}
    }

    override suspend fun verifyPassword(password: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.verifyPassword(password)
    }

    override suspend fun updateAdminPassword(
        password: String,
        confirmPassword: String,
    ): Result<Unit, DataError.Remote> {
        return remoteDataSource.updateAdminPassword(password, confirmPassword)
    }

    override suspend fun logout(): Result<Unit, DataError.Remote> {
        return remoteDataSource.logout().onSuccess {
            tokenRepository.clearTokens()
            roleRepository.clearUserRole()
        }
    }

    override suspend fun sendVerificationCodeToEmail(): Result<SendVerificationCode, DataError.Remote> {
        return remoteDataSource.sendVerificationCodeToEmail().map { it.toDomain() }
    }
}