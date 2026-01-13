package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.dto.student.StudentFCMTokenDto
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.ADD_STUDENT_FCM_TOKENS
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REMOVE_STUDENT_FCM_TOKENS
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.UPDATE_STUDENT_FCM_TOKENS
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class KtorRemoteStudentFCMTokenDataSource(
    private val httpClient: HttpClient
) : RemoteStudentFCMTokenDataSource {

    override suspend fun addStudentFCMTokens(
        studentId: String,
        fcmToken: String
    ): Result<StudentFCMTokenDto, DataError.Remote> {
        return safeCall<StudentFCMTokenDto> {
            httpClient.post(ADD_STUDENT_FCM_TOKENS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "studentId" to studentId,
                    "fcmToken" to fcmToken
                ))
            }
        }
    }

    override suspend fun updateStudentFCMTokens(
        studentId: String,
        fcmToken: String
    ): Result<StudentFCMTokenDto, DataError.Remote> {
        return safeCall<StudentFCMTokenDto> {
            httpClient.put(UPDATE_STUDENT_FCM_TOKENS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "studentId" to studentId,
                    "fcmToken" to fcmToken
                ))
            }
        }
    }

    override suspend fun removeStudentFCMTokens(studentId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REMOVE_STUDENT_FCM_TOKENS) {
                parameter("studentId", studentId)
            }
        }
    }
}