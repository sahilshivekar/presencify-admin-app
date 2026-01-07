package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.dto.student.DropoutDto
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.ADD_STUDENT_TO_DROPOUT
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_DROPOUT_BY_ID
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_DROPOUT_DETAILS_OF_STUDENT
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REMOVE_STUDENT_FROM_DROPOUT
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorRemoteDropoutDataSource(
    private val httpClient: HttpClient
) : RemoteDropoutDataSource {

    override suspend fun addStudentToDropout(
        studentId: String,
        academicStartYear: Int,
        academicEndYear: Int
    ): Result<DropoutDto, DataError.Remote> {
        return safeCall<DropoutDto> {
            httpClient.post(ADD_STUDENT_TO_DROPOUT) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "studentId" to studentId,
                    "academicStartYear" to academicStartYear,
                    "academicEndYear" to academicEndYear
                ))
            }
        }
    }

    override suspend fun removeStudentFromDropout(
        studentId: String,
        academicStartYear: Int,
        academicEndYear: Int
    ): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REMOVE_STUDENT_FROM_DROPOUT) {
                parameter("studentId", studentId)
                parameter("academicStartYear", academicStartYear)
                parameter("academicEndYear", academicEndYear)
            }
        }
    }

    override suspend fun getDropoutById(id: String): Result<DropoutDto, DataError.Remote> {
        return safeCall<DropoutDto> {
            httpClient.get("${GET_DROPOUT_BY_ID}/$id")
        }
    }

    override suspend fun getDropoutDetailsOfStudent(studentId: String): Result<List<DropoutDto>, DataError.Remote> {
        return safeCall<List<DropoutDto>> {
            httpClient.get(GET_DROPOUT_DETAILS_OF_STUDENT) {
                parameter("studentId", studentId)
            }
        }
    }
}