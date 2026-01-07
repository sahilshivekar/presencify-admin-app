package edu.watumull.presencify.core.domain.repository.attendance

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.attendance.AggregatedAttendance
import edu.watumull.presencify.core.domain.model.attendance.Attendance
import edu.watumull.presencify.core.domain.model.attendance.AttendanceStudent
import edu.watumull.presencify.core.domain.model.attendance.AttendanceSummary
import kotlinx.datetime.LocalDate

interface AttendanceRepository {
    suspend fun createAttendance(
        classId: String,
        date: LocalDate,
    ): Result<Attendance, DataError.Remote>

    suspend fun addStudentsAttendance(
        attendanceId: String,
        presentStudentIds: List<String>,
        absentStudentIds: List<String>,
    ): Result<Unit, DataError.Remote>

    suspend fun updateStudentAttendance(
        attendanceId: String,
        studentId: String,
        newAttendanceStatus: Boolean,
    ): Result<AttendanceStudent, DataError.Remote>

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
    ): Result<AggregatedAttendance, DataError.Remote>

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
    ): Result<AggregatedAttendance, DataError.Remote>

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
    ): Result<List<AttendanceSummary>, DataError.Remote>

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
    ): Result<List<Attendance>, DataError.Remote>

    suspend fun getActiveAttendanceSheet(
        studentId: String,
        divisionId: String,
    ): Result<List<Attendance>, DataError.Remote>
}