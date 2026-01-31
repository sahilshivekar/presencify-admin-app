package edu.watumull.presencify.core.data.network.teacher

import edu.watumull.presencify.core.data.dto.teacher.TeacherDto
import edu.watumull.presencify.core.data.dto.teacher.TeacherListWithTotalCountDto
import edu.watumull.presencify.core.data.dto.teacher.TeacherTeachesCourseDto
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.ADD_TEACHER
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.ADD_TEACHING_SUBJECT
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.GET_TEACHERS
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.GET_TEACHER_BY_ID
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.GET_TEACHING_COURSES
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.REMOVE_TEACHER
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.REMOVE_TEACHER_IMAGE
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.REMOVE_TEACHING_SUBJECT
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.UPDATE_TEACHER_DETAILS
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.UPDATE_TEACHER_IMAGE
import edu.watumull.presencify.core.data.network.teacher.ApiEndpoints.UPDATE_TEACHER_PASSWORD
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.data.util.FileMimeUtils.getExtensionFromMime
import edu.watumull.presencify.core.data.util.FileMimeUtils.getMimeType
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.TeacherRole
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class KtorRemoteTeacherDataSource(
    private val httpClient: HttpClient
) : RemoteTeacherDataSource {

    override suspend fun getTeachers(
        searchQuery: String?,
        courseId: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<TeacherListWithTotalCountDto, DataError.Remote> {
        return safeCall<TeacherListWithTotalCountDto> {
            httpClient.get(GET_TEACHERS) {
                searchQuery?.let { parameter("searchQuery", it) }
                courseId?.let { parameter("courseId", it) }
                page?.let { parameter("page", it) }
                limit?.let { parameter("limit", it) }
                getAll?.let { parameter("getAll", it) }
            }
        }
    }

    override suspend fun addTeacher(
        firstName: String,
        middleName: String?,
        lastName: String,
        email: String,
        phoneNumber: String,
        gender: Gender,
        highestQualification: String?,
        role: TeacherRole,
        isActive: Boolean?,
        teacherImage: ByteArray?
    ): Result<TeacherDto, DataError.Remote> {


        return safeCall<TeacherDto> {
            httpClient.post(ADD_TEACHER) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("firstName", firstName)
                            middleName?.let { append("middleName", it) }
                            append("lastName", lastName)
                            append("email", email)
                            append("phoneNumber", phoneNumber)
                            append("gender", gender.value)
                            highestQualification?.let { append("highestQualification", it) }
                            append("role", role.value)
                            isActive?.let { append("isActive", it.toString()) }
                            teacherImage?.let {
                                val mimeType = getMimeType(teacherImage)
                                val extension = getExtensionFromMime(mimeType)
                                append("teacherImageFile", teacherImage, Headers.build {
                                    append(HttpHeaders.ContentType, mimeType)
                                    append(HttpHeaders.ContentDisposition, "filename=\"teacher_new.$extension\"")
                                })
                            }
                        }
                    )
                )
            }
        }
    }

    override suspend fun getTeacherById(id: String): Result<TeacherDto, DataError.Remote> {
        return safeCall<TeacherDto> {
            httpClient.get("$GET_TEACHER_BY_ID/$id")
        }
    }

    override suspend fun updateTeacherDetails(
        id: String,
        firstName: String?,
        middleName: String?,
        lastName: String?,
        email: String?,
        role: TeacherRole?,
        gender: Gender?,
        highestQualification: String?,
        phoneNumber: String?,
    ): Result<TeacherDto, DataError.Remote> {
        return safeCall<TeacherDto> {
            httpClient.put(UPDATE_TEACHER_DETAILS) {
                contentType(ContentType.Application.Json)
                setBody(buildMap {
                    put("id", id)
                    firstName?.let { put("firstName", it) }
                    middleName?.let { put("middleName", it) }
                    lastName?.let { put("lastName", it) }
                    email?.let { put("email", it) }
                    role?.value?.let { put("role", it) }
                    gender?.value?.let { put("gender", it) }
                    highestQualification?.let { put("highestQualification", it) }
                    phoneNumber?.let { put("phoneNumber", it) }
                })
            }
        }
    }

    override suspend fun updateTeacherPassword(
        id: String,
        password: String,
        confirmPassword: String
    ): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.put(UPDATE_TEACHER_PASSWORD) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("password" to password, "confirmPassword" to confirmPassword))
            }
        }
    }

    override suspend fun updateTeacherImage(
        id: String,
        imageBytes: ByteArray?,
    ): Result<TeacherDto, DataError.Remote> {

        return safeCall<TeacherDto> {
            httpClient.put(UPDATE_TEACHER_IMAGE) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("id", id)
                            imageBytes?.let {
                                val mimeType = getMimeType(imageBytes)
                                val extension = getExtensionFromMime(mimeType)
                                append("teacherImageFile", imageBytes, Headers.build {
                                    append(HttpHeaders.ContentType, mimeType)
                                    append(HttpHeaders.ContentDisposition, "filename=\"teacher$id.$extension\"")
                                })
                            }
                        }
                    )
                )
            }
        }
    }

    override suspend fun removeTeacherImage(id: String): Result<TeacherDto, DataError.Remote> {
        return safeCall<TeacherDto> {
            httpClient.delete("$REMOVE_TEACHER_IMAGE/$id")
        }
    }

    override suspend fun removeTeacher(id: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_TEACHER/$id")
        }
    }

    override suspend fun getTeachingCourses(teacherId: String): Result<List<TeacherTeachesCourseDto>, DataError.Remote> {
        return safeCall<List<TeacherTeachesCourseDto>> {
            httpClient.get(GET_TEACHING_COURSES) {
                parameter("teacherId", teacherId)
            }
        }
    }

    override suspend fun addTeachingCourse(
        teacherId: String,
        courseId: String
    ): Result<TeacherTeachesCourseDto, DataError.Remote> {
        return safeCall<TeacherTeachesCourseDto> {
            httpClient.post(ADD_TEACHING_SUBJECT) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("teacherId" to teacherId, "courseId" to courseId))
            }
        }
    }

    override suspend fun removeTeachingCourse(
        teacherTeachesCourseId: String
    ): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete("$REMOVE_TEACHING_SUBJECT/$teacherTeachesCourseId")
        }
    }


    override suspend fun bulkCreateTeachers(teachers: List<Map<String, Any>>): Result<List<TeacherDto>, DataError.Remote> {
        return safeCall<List<TeacherDto>> {
            httpClient.post(ApiEndpoints.BULK_CREATE_TEACHERS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("teachers" to teachers))
            }
        }
    }

    override suspend fun bulkDeleteTeachers(teacherIds: List<String>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(ApiEndpoints.BULK_DELETE_TEACHERS) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("teacherIds" to teacherIds))
            }
        }
    }

    override suspend fun bulkCreateTeachersFromCSV(csvData: ByteArray): Result<List<TeacherDto>, DataError.Remote> {
        return safeCall<List<TeacherDto>> {
            httpClient.post(ApiEndpoints.BULK_CREATE_TEACHERS_FROM_CSV) {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append("csvFile", csvData, Headers.build {
                                append(HttpHeaders.ContentType, "text/csv")
                                append(HttpHeaders.ContentDisposition, "filename=\"teachers.csv\"")
                            })
                        }
                    ))
            }
        }
    }
}
