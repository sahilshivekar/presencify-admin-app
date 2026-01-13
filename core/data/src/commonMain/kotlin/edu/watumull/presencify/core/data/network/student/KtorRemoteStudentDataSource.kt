package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.dto.student.*
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.ADD_STUDENT
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.ADD_STUDENT_TO_BATCH
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.ADD_STUDENT_TO_DIVISION
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.ADD_STUDENT_TO_SEMESTER
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.CHANGE_STUDENT_BATCH
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.CHANGE_STUDENT_DIVISION
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_STUDENTS
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_STUDENT_BATCHES
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_STUDENT_BY_ID
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_STUDENT_DIVISIONS
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.GET_STUDENT_SEMESTERS
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REMOVE_STUDENT
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REMOVE_STUDENT_FROM_SEMESTER
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REMOVE_STUDENT_IMAGE
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REVERT_ADD_STUDENT_TO_BATCH
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REVERT_ADD_STUDENT_TO_DIVISION
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REVERT_CHANGE_STUDENT_BATCH
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.REVERT_CHANGE_STUDENT_DIVISION
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.UPDATE_STUDENT_DETAILS
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.UPDATE_STUDENT_IMAGE
import edu.watumull.presencify.core.data.network.student.ApiEndpoints.UPDATE_STUDENT_PASSWORD
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.data.util.FileMimeUtils
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.AdmissionType
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.datetime.LocalDate

class KtorRemoteStudentDataSource(
    private val httpClient: HttpClient,
) : RemoteStudentDataSource {

    override suspend fun getStudents(
        searchQuery: String?,
        branchIds: List<String>?,
        semesterNumbers: List<Int>?,
        academicStartYearOfSemester: Int?,
        academicEndYearOfSemester: Int?,
        semesterId: String?,
        batchId: String?,
        schemeId: String?,
        divisionId: String?,
        dropoutAcademicStartYear: Int?,
        dropoutAcademicEndYear: Int?,
        admissionTypes: List<AdmissionType>?,
        admissionYear: Int?,
        currentBatch: Boolean?,
        currentDivision: Boolean?,
        currentSemester: Boolean?,
        divisionCode: String?,
        batchCode: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?,
    ): Result<StudentListWithTotalCountDto, DataError.Remote> {
        return safeCall<StudentListWithTotalCountDto> {
            httpClient.get(GET_STUDENTS) {
                searchQuery?.let { parameter("searchQuery", it) }
                branchIds?.let { parameter("branchIds", it) }
                semesterNumbers?.let { parameter("semesterNumbers", it) }
                academicStartYearOfSemester?.let { parameter("academicStartYearOfSemester", it) }
                academicEndYearOfSemester?.let { parameter("academicEndYearOfSemester", it) }
                semesterId?.let { parameter("semesterId", it) }
                batchId?.let { parameter("batchId", it) }
                schemeId?.let { parameter("schemeId", it) }
                divisionId?.let { parameter("divisionId", it) }
                dropoutAcademicStartYear?.let { parameter("dropoutAcademicStartYear", it) }
                dropoutAcademicEndYear?.let { parameter("dropoutAcademicEndYear", it) }
                admissionTypes?.map { it.value }?.let { parameter("admissionTypes", it) }
                admissionYear?.let { parameter("admissionYear", it) }
                currentBatch?.let { parameter("currentBatch", it) }
                currentDivision?.let { parameter("currentDivision", it) }
                currentSemester?.let { parameter("currentSemester", it) }
                divisionCode?.let { parameter("divisionCode", it) }
                batchCode?.let { parameter("batchCode", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun addStudent(
        prn: String,
        firstName: String,
        middleName: String?,
        lastName: String,
        email: String,
        phoneNumber: String,
        gender: Gender,
        dob: String?,
        schemeId: String,
        admissionYear: Int,
        admissionType: AdmissionType,
        branchId: String,
        parentEmail: String?,
        studentImage: ByteArray,
    ): Result<StudentDto, DataError.Remote> {
        val mimeType = FileMimeUtils.getMimeType(studentImage)
        val extension = FileMimeUtils.getExtensionFromMime(mimeType)

        return safeCall<StudentDto> {
            httpClient.post(ADD_STUDENT) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("prn", prn)
                            append("firstName", firstName)
                            middleName?.let { append("middleName", it) }
                            append("lastName", lastName)
                            append("email", email)
                            append("phoneNumber", phoneNumber)
                            append("gender", gender.value)
                            dob?.let { append("dob", it) }
                            append("schemeId", schemeId)
                            append("admissionYear", admissionYear.toString())
                            append("admissionType", admissionType.value)
                            append("branchId", branchId)
                            parentEmail?.let { append("parentEmail", it) }

                            append("studentImageFile", studentImage, Headers.build {
                                append(HttpHeaders.ContentType, mimeType)
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=\"student_new.$extension\""
                                )
                            })
                        }
                    )
                )
            }
        }
    }

    override suspend fun getStudentById(id: String): Result<StudentDto, DataError.Remote> {
        return safeCall<StudentDto> {
            httpClient.get("${GET_STUDENT_BY_ID}/$id")
        }
    }

    override suspend fun updateStudentDetails(
        id: String,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        gender: Gender?,
        phoneNumber: String?,
        dob: String?,
        schemeId: String?,
        branchId: String?,
        parentEmail: String?,
        prn: String?,
        admissionYear: Int?,
        admissionType: AdmissionType?,
    ): Result<StudentDto, DataError.Remote> {
        return safeCall<StudentDto> {
            httpClient.put("${UPDATE_STUDENT_DETAILS}/$id") {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    firstName?.let { put("firstName", it) }
                    middleName?.let { put("middleName", it) }
                    lastName?.let { put("lastName", it) }
                    email?.let { put("email", it) }
                    gender?.value?.let { put("gender", it) }
                    phoneNumber?.let { put("phoneNumber", it) }
                    dob?.let { put("dob", it) }
                    schemeId?.let { put("schemeId", it) }
                    branchId?.let { put("branchId", it) }
                    parentEmail?.let { put("parentEmail", it) }
                    prn?.let { put("prn", it) }
                    admissionYear?.let { put("admissionYear", it) }
                    admissionType?.value?.let { put("admissionType", it) }
                })
            }
        }
    }

    override suspend fun updateStudentPassword(
        id: String,
        password: String,
        confirmPassword: String,
    ): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.put(UPDATE_STUDENT_PASSWORD) {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "id" to id,
                        "password" to password,
                        "confirmPassword" to confirmPassword
                    )
                )
            }
        }
    }

    override suspend fun updateStudentImage(
        id: String,
        imageBytes: ByteArray,
    ): Result<StudentDto, DataError.Remote> {
        val mimeType = FileMimeUtils.getMimeType(imageBytes)
        val extension = FileMimeUtils.getExtensionFromMime(mimeType)

        return safeCall<StudentDto> {
            httpClient.put(UPDATE_STUDENT_IMAGE) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("id", id)
                            append("studentImageFile", imageBytes, Headers.build {
                                append(HttpHeaders.ContentType, mimeType)
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=\"student_$id.$extension\""
                                )
                            })
                        }
                    )
                )
            }
        }
    }

    override suspend fun removeStudentImage(studentId: String): Result<StudentDto, DataError.Remote> {
        return safeCall<StudentDto> {
            httpClient.delete(REMOVE_STUDENT_IMAGE) {
                parameter("studentId", studentId)
            }
        }
    }

    override suspend fun removeStudent(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("${REMOVE_STUDENT}/$id")
        }
    }

    override suspend fun getStudentSemesters(id: String): Result<List<StudentSemesterDto>, DataError.Remote> {
        return safeCall<List<StudentSemesterDto>> {
            httpClient.get("${GET_STUDENT_SEMESTERS}/$id/semesters")
        }
    }

    override suspend fun addStudentToSemester(
        studentId: String,
        semesterId: String,
    ): Result<StudentSemesterDto, DataError.Remote> {
        return safeCall<StudentSemesterDto> {
            httpClient.post(ADD_STUDENT_TO_SEMESTER) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentId" to studentId, "semesterId" to semesterId))
            }
        }
    }

    override suspend fun removeStudentFromSemester(studentSemesterId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REMOVE_STUDENT_FROM_SEMESTER) {
                parameter("studentSemesterId", studentSemesterId)
            }
        }
    }

    override suspend fun getStudentDivisions(
        id: String,
        semesterNumber: SemesterNumber?,
    ): Result<List<StudentDivisionDto>, DataError.Remote> {
        return safeCall<List<StudentDivisionDto>> {
            httpClient.get("${GET_STUDENT_DIVISIONS}/$id/divisions") {
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
            }
        }
    }

    override suspend fun addStudentToDivision(
        studentId: String,
        divisionId: String,
    ): Result<StudentDivisionDto, DataError.Remote> {
        return safeCall<StudentDivisionDto> {
            httpClient.post("${ADD_STUDENT_TO_DIVISION}/$studentId/divisions") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("divisionId" to divisionId))
            }
        }
    }

    override suspend fun changeStudentDivision(
        studentDivisionId: String,
        divisionId: String,
        newDivisionStartDate: LocalDate,
    ): Result<StudentDivisionDto, DataError.Remote> {
        return safeCall<StudentDivisionDto> {
            httpClient.put(CHANGE_STUDENT_DIVISION) {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "studentDivisionId" to studentDivisionId,
                        "divisionId" to divisionId,
                        "newDivisionStartDate" to newDivisionStartDate
                    )
                )
            }
        }
    }

    override suspend fun revertAddStudentToDivision(studentDivisionId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REVERT_ADD_STUDENT_TO_DIVISION) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentDivisionId" to studentDivisionId))
            }
        }
    }

    override suspend fun revertChangeStudentDivision(newStudentDivisionId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REVERT_CHANGE_STUDENT_DIVISION) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("newStudentDivisionId" to newStudentDivisionId))
            }
        }
    }

    override suspend fun getStudentBatches(
        id: String,
        semesterNumber: SemesterNumber?,
    ): Result<List<StudentBatchDto>, DataError.Remote> {
        return safeCall<List<StudentBatchDto>> {
            httpClient.get("${GET_STUDENT_BATCHES}/$id/batches") {
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
            }
        }
    }

    override suspend fun addStudentToBatch(
        studentId: String,
        batchId: String,
    ): Result<StudentBatchDto, DataError.Remote> {
        return safeCall<StudentBatchDto> {
            httpClient.post("${ADD_STUDENT_TO_BATCH}/$studentId/batches") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("batchId" to batchId))
            }
        }
    }

    override suspend fun changeStudentBatch(
        studentBatchId: String,
        batchId: String,
        newBatchStartDate: LocalDate,
    ): Result<StudentBatchDto, DataError.Remote> {
        return safeCall<StudentBatchDto> {
            httpClient.put(CHANGE_STUDENT_BATCH) {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        "studentBatchId" to studentBatchId,
                        "batchId" to batchId,
                        "newBatchStartDate" to newBatchStartDate
                    )
                )
            }
        }
    }

    override suspend fun revertAddStudentToBatch(studentBatchId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REVERT_ADD_STUDENT_TO_BATCH) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentBatchId" to studentBatchId))
            }
        }
    }

    override suspend fun revertChangeStudentBatch(newStudentBatchId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REVERT_CHANGE_STUDENT_BATCH) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("newStudentBatchId" to newStudentBatchId))
            }
        }
    }

    override suspend fun bulkCreateStudents(students: List<Map<String, Any?>>): Result<List<StudentDto>, DataError.Remote> {
        return safeCall<List<StudentDto>> {
            httpClient.post(ApiEndpoints.BULK_CREATE_STUDENTS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("students" to students))
            }
        }
    }

    override suspend fun bulkDeleteStudents(studentIds: List<String>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(ApiEndpoints.BULK_DELETE_STUDENTS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentIds" to studentIds))
            }
        }
    }

    override suspend fun bulkAddStudentsToSemester(
        studentIds: List<String>,
        semesterId: String,
    ): Result<List<StudentSemesterDto>, DataError.Remote> {
        return safeCall<List<StudentSemesterDto>> {
            httpClient.post(ApiEndpoints.BULK_ADD_STUDENTS_TO_SEMESTER) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentIds" to studentIds, "semesterId" to semesterId))
            }
        }
    }

    override suspend fun bulkAddStudentsToDivision(
        studentIds: List<String>,
        divisionId: String,
    ): Result<List<StudentDivisionDto>, DataError.Remote> {
        return safeCall<List<StudentDivisionDto>> {
            httpClient.post(ApiEndpoints.BULK_ADD_STUDENTS_TO_DIVISION) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentIds" to studentIds, "divisionId" to divisionId))
            }
        }
    }

    override suspend fun bulkAddStudentsToBatch(
        studentIds: List<String>,
        batchId: String,
    ): Result<List<StudentBatchDto>, DataError.Remote> {
        return safeCall<List<StudentBatchDto>> {
            httpClient.post(ApiEndpoints.BULK_ADD_STUDENTS_TO_BATCH) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("studentIds" to studentIds, "batchId" to batchId))
            }
        }
    }

    override suspend fun bulkCreateStudentsFromCSV(csvData: ByteArray): Result<List<StudentDto>, DataError.Remote> {
        return safeCall<List<StudentDto>> {
            httpClient.post(ApiEndpoints.BULK_CREATE_STUDENTS_FROM_CSV) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("csvFile", csvData, io.ktor.http.Headers.build {
                                append(io.ktor.http.HttpHeaders.ContentType, "text/csv")
                                append(
                                    io.ktor.http.HttpHeaders.ContentDisposition,
                                    "filename=\"students.csv\""
                                )
                            })
                        }
                    ))
            }
        }
    }
}
