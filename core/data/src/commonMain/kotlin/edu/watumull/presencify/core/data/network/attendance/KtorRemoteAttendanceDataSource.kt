package edu.watumull.presencify.core.data.network.attendance

import edu.watumull.presencify.core.data.dto.attendance.AggregatedAttendanceDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceStudentDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceSummaryDto
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.ADD_STUDENTS_ATTENDANCE
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.BULK_UPDATE_STUDENT_ATTENDANCE
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.CREATE_ATTENDANCE
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.GET_ACTIVE_ATTENDANCE_SHEET
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.GET_ATTENDANCE
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.GET_ATTENDANCE_OF_ALL
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.GET_ATTENDANCE_OF_STUDENT
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.REMOVE_ATTENDANCE
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.SEND_ATTENDANCE_REPORT
import edu.watumull.presencify.core.data.network.attendance.ApiEndpoints.UPDATE_STUDENT_ATTENDANCE
import edu.watumull.presencify.core.data.repository.safeCall
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.LocalDate

class KtorRemoteAttendanceDataSource(
    private val httpClient: HttpClient
) : RemoteAttendanceDataSource {

    override suspend fun createAttendance(classId: String, date: LocalDate): Result<AttendanceDto, DataError.Remote> {
        return safeCall<AttendanceDto> {
            httpClient.post(CREATE_ATTENDANCE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("classId" to classId, "date" to date))
            }
        }
    }

    override suspend fun addStudentsAttendance(attendanceId: String, presentStudentIds: List<String>, absentStudentIds: List<String>): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.post(ADD_STUDENTS_ATTENDANCE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("attendanceId" to attendanceId, "presentStudentIds" to presentStudentIds, "absentStudentIds" to absentStudentIds))
            }
        }
    }

    override suspend fun updateStudentAttendance(attendanceId: String, studentId: String, newAttendanceStatus: Boolean): Result<AttendanceStudentDto, DataError.Remote> {
        return safeCall<AttendanceStudentDto> {
            httpClient.put(UPDATE_STUDENT_ATTENDANCE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("attendanceId" to attendanceId, "studentId" to studentId, "newAttendanceStatus" to newAttendanceStatus))
            }
        }
    }

    override suspend fun bulkUpdateStudentAttendance(attendanceUpdates: List<Map<String, Any>>): Result<Map<String, Any>, DataError.Remote> {
        return safeCall<Map<String, Any>> {
            httpClient.put(BULK_UPDATE_STUDENT_ATTENDANCE) {
                contentType(ContentType.Application.Json)
                setBody(mapOf("attendanceUpdates" to attendanceUpdates))
            }
        }
    }

    override suspend fun removeAttendance(attendanceId: String): Result<Unit, DataError.Remote> {
        return safeCall<Unit> {
            httpClient.delete(REMOVE_ATTENDANCE) {
                parameter("attendanceId", attendanceId)
            }
        }
    }

    override suspend fun getAttendanceOfAnyStudentForSpecificCourseInSemester(
        studentId: String, courseId: String, semesterId: String?, divisionId: String?, batchId: String?,
        startDate: LocalDate?, endDate: LocalDate?, semesterNumber: SemesterNumber?, academicStartYear: Int?, academicEndYear: Int?,
        branchId: String?, schemeId: String?
    ): Result<AggregatedAttendanceDto, DataError.Remote> {
        return safeCall<AggregatedAttendanceDto> {
            httpClient.get(GET_ATTENDANCE_OF_STUDENT) {
                parameter("studentId", studentId)
                parameter("courseId", courseId)
                semesterId?.let { parameter("semesterId", it) }
                divisionId?.let { parameter("divisionId", it) }
                batchId?.let { parameter("batchId", it) }
                startDate?.let { parameter("startDate", it) }
                endDate?.let { parameter("endDate", it) }
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                academicStartYear?.let { parameter("academicStartYear", it) }
                academicEndYear?.let { parameter("academicEndYear", it) }
                branchId?.let { parameter("branchId", it) }
                schemeId?.let { parameter("schemeId", it) }
            }
        }
    }

    override suspend fun getAttendanceOfSelfForSpecificCourseInSemester(
        courseId: String, semesterId: String?, divisionId: String?, batchId: String?,
        startDate: LocalDate?, endDate: LocalDate?, semesterNumber: SemesterNumber?, academicStartYear: Int?, academicEndYear: Int?,
        branchId: String?, schemeId: String?
    ): Result<AggregatedAttendanceDto, DataError.Remote> {
        return safeCall<AggregatedAttendanceDto> {
            httpClient.get(GET_ATTENDANCE_OF_STUDENT) {
                parameter("courseId", courseId)
                semesterId?.let { parameter("semesterId", it) }
                divisionId?.let { parameter("divisionId", it) }
                batchId?.let { parameter("batchId", it) }
                startDate?.let { parameter("startDate", it) }
                endDate?.let { parameter("endDate", it) }
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                academicStartYear?.let { parameter("academicStartYear", it) }
                academicEndYear?.let { parameter("academicEndYear", it) }
                branchId?.let { parameter("branchId", it) }
                schemeId?.let { parameter("schemeId", it) }
            }
        }
    }

    override suspend fun getAttendanceOfAllForSemesterDivisionBatchCourse(
        semesterId: String?, divisionId: String?, batchId: String?, courseId: String?, startDate: LocalDate?, endDate: LocalDate?,
        semesterNumber: SemesterNumber?, academicStartYear: Int?, academicEndYear: Int?, branchId: String?, schemeId: String?
    ): Result<List<AttendanceSummaryDto>, DataError.Remote> {
        return safeCall<List<AttendanceSummaryDto>> {
            httpClient.get(GET_ATTENDANCE_OF_ALL) {
                semesterId?.let { parameter("semesterId", it) }
                divisionId?.let { parameter("divisionId", it) }
                batchId?.let { parameter("batchId", it) }
                courseId?.let { parameter("courseId", it) }
                startDate?.let { parameter("startDate", it) }
                endDate?.let { parameter("endDate", it) }
                semesterNumber?.value?.let { parameter("semesterNumber", it) }
                academicStartYear?.let { parameter("academicStartYear", it) }
                academicEndYear?.let { parameter("academicEndYear", it) }
                branchId?.let { parameter("branchId", it) }
                schemeId?.let { parameter("schemeId", it) }
            }
        }
    }

    override suspend fun sendAttendanceReport(
        startDate: LocalDate?, endDate: LocalDate?, studentIds: List<String>?, courseIds: List<String>?, semesterId: String?
    ): Result<Map<String, Any>, DataError.Remote> {
        return safeCall<Map<String, Any>> {
            httpClient.post(SEND_ATTENDANCE_REPORT) {
                contentType(ContentType.Application.Json)
                setBody(mapOf(
                    "startDate" to startDate,
                    "endDate" to endDate,
                    "studentIds" to studentIds,
                    "courseIds" to courseIds,
                    "semesterId" to semesterId
                ))
            }
        }
    }

    override suspend fun getAttendance(
        date: LocalDate?, attendanceId: String?, classId: String?, studentId: String?, courseId: String?, semesterId: String?,
        divisionId: String?, batchId: String?, startDate: LocalDate?, endDate: LocalDate?
    ): Result<List<AttendanceDto>, DataError.Remote> {
        return safeCall<List<AttendanceDto>> {
            httpClient.get(GET_ATTENDANCE) {
                date?.let { parameter("date", it) }
                attendanceId?.let { parameter("attendanceId", it) }
                classId?.let { parameter("classId", it) }
                studentId?.let { parameter("studentId", it) }
                courseId?.let { parameter("courseId", it) }
                semesterId?.let { parameter("semesterId", it) }
                divisionId?.let { parameter("divisionId", it) }
                batchId?.let { parameter("batchId", it) }
                startDate?.let { parameter("startDate", it) }
                endDate?.let { parameter("endDate", it) }
            }
        }
    }

    override suspend fun getActiveAttendanceSheet(studentId: String, divisionId: String): Result<List<AttendanceDto>, DataError.Remote> {
        return safeCall<List<AttendanceDto>> {
            httpClient.get(GET_ACTIVE_ATTENDANCE_SHEET) {
                parameter("studentId", studentId)
                parameter("divisionId", divisionId)
            }
        }
    }
}