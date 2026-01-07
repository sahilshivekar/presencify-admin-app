package edu.watumull.presencify.core.data.repository.attendance

import edu.watumull.presencify.core.data.mapper.attendance.toDomain
import edu.watumull.presencify.core.data.network.attendance.RemoteAttendanceDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.attendance.AggregatedAttendance
import edu.watumull.presencify.core.domain.model.attendance.Attendance
import edu.watumull.presencify.core.domain.model.attendance.AttendanceStudent
import edu.watumull.presencify.core.domain.model.attendance.AttendanceSummary
import edu.watumull.presencify.core.domain.repository.attendance.AttendanceRepository
import kotlinx.datetime.LocalDate

class AttendanceRepositoryImpl(
    private val remoteDataSource: RemoteAttendanceDataSource
) : AttendanceRepository {

    override suspend fun createAttendance(classId: String, date: LocalDate): Result<Attendance, DataError.Remote> {
        return remoteDataSource.createAttendance(classId, date).map { it.toDomain() }
    }

    override suspend fun addStudentsAttendance(attendanceId: String, presentStudentIds: List<String>, absentStudentIds: List<String>): Result<Unit, DataError.Remote> {
        return remoteDataSource.addStudentsAttendance(attendanceId, presentStudentIds, absentStudentIds)
    }

    override suspend fun updateStudentAttendance(attendanceId: String, studentId: String, newAttendanceStatus: Boolean): Result<AttendanceStudent, DataError.Remote> {
        return remoteDataSource.updateStudentAttendance(attendanceId, studentId, newAttendanceStatus).map { it.toDomain() }
    }

    override suspend fun bulkUpdateStudentAttendance(attendanceUpdates: List<Map<String, Any>>): Result<Map<String, Any>, DataError.Remote> {
        return remoteDataSource.bulkUpdateStudentAttendance(attendanceUpdates)
    }

    override suspend fun removeAttendance(attendanceId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeAttendance(attendanceId)
    }

    override suspend fun getAttendanceOfAnyStudentForSpecificCourseInSemester(
        studentId: String, courseId: String, semesterId: String?, divisionId: String?, batchId: String?,
        startDate: LocalDate?, endDate: LocalDate?, semesterNumber: SemesterNumber?, academicStartYear: Int?, academicEndYear: Int?,
        branchId: String?, schemeId: String?
    ): Result<AggregatedAttendance, DataError.Remote> {
        return remoteDataSource.getAttendanceOfAnyStudentForSpecificCourseInSemester(
            studentId, courseId, semesterId, divisionId, batchId, startDate, endDate, semesterNumber, academicStartYear, academicEndYear, branchId, schemeId
        ).map { it.toDomain() }
    }

    override suspend fun getAttendanceOfSelfForSpecificCourseInSemester(
        courseId: String, semesterId: String?, divisionId: String?, batchId: String?,
        startDate: LocalDate?, endDate: LocalDate?, semesterNumber: SemesterNumber?, academicStartYear: Int?, academicEndYear: Int?,
        branchId: String?, schemeId: String?
    ): Result<AggregatedAttendance, DataError.Remote> {
        return remoteDataSource.getAttendanceOfSelfForSpecificCourseInSemester(
            courseId, semesterId, divisionId, batchId, startDate, endDate, semesterNumber, academicStartYear, academicEndYear, branchId, schemeId
        ).map { it.toDomain() }
    }

    override suspend fun getAttendanceOfAllForSemesterDivisionBatchCourse(
        semesterId: String?, divisionId: String?, batchId: String?, courseId: String?, startDate: LocalDate?, endDate: LocalDate?,
        semesterNumber: SemesterNumber?, academicStartYear: Int?, academicEndYear: Int?, branchId: String?, schemeId: String?
    ): Result<List<AttendanceSummary>, DataError.Remote> {
        return remoteDataSource.getAttendanceOfAllForSemesterDivisionBatchCourse(
            semesterId, divisionId, batchId, courseId, startDate, endDate, semesterNumber, academicStartYear, academicEndYear, branchId, schemeId
        ).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun sendAttendanceReport(
        startDate: LocalDate?, endDate: LocalDate?, studentIds: List<String>?, courseIds: List<String>?, semesterId: String?
    ): Result<Map<String, Any>, DataError.Remote> {
        return remoteDataSource.sendAttendanceReport(startDate, endDate, studentIds, courseIds, semesterId)
    }

    override suspend fun getAttendance(
        date: LocalDate?, attendanceId: String?, classId: String?, studentId: String?, courseId: String?, semesterId: String?,
        divisionId: String?, batchId: String?, startDate: LocalDate?, endDate: LocalDate?
    ): Result<List<Attendance>, DataError.Remote> {
        return remoteDataSource.getAttendance(
            date, attendanceId, classId, studentId, courseId, semesterId, divisionId, batchId, startDate, endDate
        ).map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getActiveAttendanceSheet(studentId: String, divisionId: String): Result<List<Attendance>, DataError.Remote> {
        return remoteDataSource.getActiveAttendanceSheet(studentId, divisionId).map { list -> list.map { it.toDomain() } }
    }
}