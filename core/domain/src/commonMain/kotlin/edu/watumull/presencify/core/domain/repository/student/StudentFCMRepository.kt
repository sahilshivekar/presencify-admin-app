package edu.watumull.presencify.core.domain.repository.student

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.model.student.StudentFCMToken

interface StudentFCMRepository {
    suspend fun addStudentFCMTokens(studentId: String, fcmToken: String): Result<StudentFCMToken, DataError.Remote>
    suspend fun updateStudentFCMTokens(studentId: String, fcmToken: String): Result<StudentFCMToken, DataError.Remote>
    suspend fun removeStudentFCMTokens(studentId: String): Result<Unit, DataError.Remote>
}