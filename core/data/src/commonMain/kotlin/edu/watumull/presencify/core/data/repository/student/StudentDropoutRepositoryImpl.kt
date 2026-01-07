package edu.watumull.presencify.core.data.repository.student

import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.data.network.student.RemoteDropoutDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.student.Dropout
import edu.watumull.presencify.core.domain.repository.student.StudentDropoutRepository

class StudentDropoutRepositoryImpl(
    private val remoteDataSource: RemoteDropoutDataSource
) : StudentDropoutRepository {

    override suspend fun addStudentToDropout(
        studentId: String,
        academicStartYear: Int,
        academicEndYear: Int
    ): Result<Dropout, DataError.Remote> {
        return remoteDataSource.addStudentToDropout(studentId, academicStartYear, academicEndYear)
            .map { it.toDomain() }
    }

    override suspend fun removeStudentFromDropout(
        studentId: String,
        academicStartYear: Int,
        academicEndYear: Int
    ): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeStudentFromDropout(studentId, academicStartYear, academicEndYear)
    }

    override suspend fun getDropoutById(id: String): Result<Dropout, DataError.Remote> {
        return remoteDataSource.getDropoutById(id).map { it.toDomain() }
    }

    override suspend fun getDropoutDetailsOfStudent(studentId: String): Result<List<Dropout>, DataError.Remote> {
        return remoteDataSource.getDropoutDetailsOfStudent(studentId).map { list ->
            list.map { it.toDomain() }
        }
    }
}