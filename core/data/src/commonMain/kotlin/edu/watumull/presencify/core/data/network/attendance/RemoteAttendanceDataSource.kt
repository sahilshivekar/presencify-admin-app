package edu.watumull.presencify.core.data.network.attendance

import edu.watumull.presencify.core.data.dto.attendance.AggregatedAttendanceDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceStudentDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceSummaryDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import kotlinx.datetime.LocalDate

interface RemoteAttendanceDataSource {
    suspend fun createAttendance(
        classId: String,
        date: LocalDate,
    ): Result<AttendanceDto, DataError.Remote>

    suspend fun addStudentsAttendance(
        attendanceId: String,
        presentStudentIds: List<String>,
        absentStudentIds: List<String>,
    ): Result<Unit, DataError.Remote>

    suspend fun updateStudentAttendance(
        attendanceId: String,
        studentId: String,
        newAttendanceStatus: Boolean,
    ): Result<AttendanceStudentDto, DataError.Remote>

    suspend fun bulkUpdateStudentAttendance(attendanceUpdates: List<Map<String, Any>>): Result<Map<String, Any>, DataError.Remote>

    suspend fun removeAttendance(attendanceId: String): Result<Unit, DataError.Remote>

    suspend fun getAttendanceOfAnyStudentForSpecificCourseInSemester(
        studentId: String,
        courseId: String,
        semesterId: String?,
        divisionId: String?,
        batchId: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        semesterNumber: SemesterNumber?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        branchId: String?,
        schemeId: String?,
    ): Result<AggregatedAttendanceDto, DataError.Remote>

    suspend fun getAttendanceOfSelfForSpecificCourseInSemester(
        courseId: String,
        semesterId: String?,
        divisionId: String?,
        batchId: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        semesterNumber: SemesterNumber?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        branchId: String?,
        schemeId: String?,
    ): Result<AggregatedAttendanceDto, DataError.Remote>

    suspend fun getAttendanceOfAllForSemesterDivisionBatchCourse(
        semesterId: String?,
        divisionId: String?,
        batchId: String?,
        courseId: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        semesterNumber: SemesterNumber?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        branchId: String?,
        schemeId: String?,
    ): Result<List<AttendanceSummaryDto>, DataError.Remote>

    suspend fun sendAttendanceReport(
        startDate: LocalDate?,
        endDate: LocalDate?,
        studentIds: List<String>?,
        courseIds: List<String>?,
        semesterId: String?,
    ): Result<Map<String, Any>, DataError.Remote>

    suspend fun getAttendance(
        date: LocalDate?,
        attendanceId: String?,
        classId: String?,
        studentId: String?,
        courseId: String?,
        semesterId: String?,
        divisionId: String?,
        batchId: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
    ): Result<List<AttendanceDto>, DataError.Remote>

    suspend fun getActiveAttendanceSheet(
        studentId: String,
        divisionId: String,
    ): Result<List<AttendanceDto>, DataError.Remote>
}