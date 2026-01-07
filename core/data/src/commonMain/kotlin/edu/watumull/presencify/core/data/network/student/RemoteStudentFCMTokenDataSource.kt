package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.dto.student.StudentFCMTokenDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteStudentFCMTokenDataSource {
    suspend fun addStudentFCMTokens(studentId: String, fcmToken: String): Result<StudentFCMTokenDto, DataError.Remote>
    suspend fun updateStudentFCMTokens(studentId: String, fcmToken: String): Result<StudentFCMTokenDto, DataError.Remote>
    suspend fun removeStudentFCMTokens(studentId: String): Result<Unit, DataError.Remote>
}