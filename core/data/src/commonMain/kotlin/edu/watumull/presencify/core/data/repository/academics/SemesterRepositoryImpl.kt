package edu.watumull.presencify.core.data.repository.academics

import edu.watumull.presencify.core.data.mapper.academics.toDomain
import edu.watumull.presencify.core.data.network.academics.RemoteSemesterDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.academics.Semester
import edu.watumull.presencify.core.domain.model.academics.SemesterListWithTotalCount
import edu.watumull.presencify.core.domain.repository.academics.SemesterRepository
import kotlinx.datetime.LocalDate

class SemesterRepositoryImpl(
    private val remoteSemesterDataSource: RemoteSemesterDataSource
) : SemesterRepository {

    override suspend fun getSemesters(
        semesterNumber: SemesterNumber?,
        academicStartYear: Int?,
        academicEndYear: Int?,
        branchId: String?,
        schemeId: String?,
        page: Int?,
        limit: Int?,
        getAll: Boolean?
    ): Result<SemesterListWithTotalCount, DataError.Remote> {
        return remoteSemesterDataSource.getSemesters(
            semesterNumber, academicStartYear, academicEndYear, branchId, schemeId, page, limit, getAll
        ).map { dto ->
            SemesterListWithTotalCount(
                totalCount = dto.totalCount,
                semesters = dto.semesters.map { it.toDomain() }
            )
        }
    }

    override suspend fun addSemester(
        branchId: String,
        semesterNumber: SemesterNumber,
        academicStartYear: Int,
        academicEndYear: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        schemeId: String,
        optionalCourseIds: List<String>?
    ): Result<Semester, DataError.Remote> {
        return remoteSemesterDataSource.addSemester(
            branchId, semesterNumber, academicStartYear, academicEndYear, startDate, endDate, schemeId, optionalCourseIds
        ).map { it.toDomain() }
    }

    override suspend fun getSemesterById(id: String): Result<Semester, DataError.Remote> {
        return remoteSemesterDataSource.getSemesterById(id).map { it.toDomain() }
    }

    override suspend fun updateSemester(
        id: String,
        startDate: LocalDate,
        endDate: LocalDate,
        optionalCourseIds: List<String>?
    ): Result<Semester, DataError.Remote> {
        return remoteSemesterDataSource.updateSemester(id, startDate, endDate, optionalCourseIds).map { it.toDomain() }
    }

    override suspend fun removeSemester(id: String): Result<Unit, DataError.Remote> {
        return remoteSemesterDataSource.removeSemester(id)
    }

    override suspend fun getCoursesOfSemester(semesterId: String): Result<List<Course>, DataError.Remote> {
        return remoteSemesterDataSource.getCoursesOfSemester(semesterId).map { it.map { course -> course.toDomain() } }
    }

    override suspend fun bulkCreateSemesters(semesters: List<Map<String, Any>>): Result<List<Semester>, DataError.Remote> {
        return remoteSemesterDataSource.bulkCreateSemesters(semesters).map { it.map { semester -> semester.toDomain() } }
    }

    override suspend fun bulkDeleteSemesters(semesterIds: List<String>): Result<Unit, DataError.Remote> {
        return remoteSemesterDataSource.bulkDeleteSemesters(semesterIds)
    }
}