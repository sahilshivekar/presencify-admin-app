package edu.watumull.presencify.core.data.repository.student

import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.data.network.student.RemoteStudentFCMTokenDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.student.StudentFCMToken
import edu.watumull.presencify.core.domain.repository.student.StudentFCMRepository

class StudentFCMRepositoryImpl(
    private val remoteDataSource: RemoteStudentFCMTokenDataSource
) : StudentFCMRepository {

    override suspend fun addStudentFCMTokens(
        studentId: String,
        fcmToken: String
    ): Result<StudentFCMToken, DataError.Remote> {
        return remoteDataSource.addStudentFCMTokens(studentId, fcmToken)
            .map { it.toDomain() }
    }

    override suspend fun updateStudentFCMTokens(
        studentId: String,
        fcmToken: String
    ): Result<StudentFCMToken, DataError.Remote> {
        return remoteDataSource.updateStudentFCMTokens(studentId, fcmToken)
            .map { it.toDomain() }
    }

    override suspend fun removeStudentFCMTokens(studentId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeStudentFCMTokens(studentId)
    }
}