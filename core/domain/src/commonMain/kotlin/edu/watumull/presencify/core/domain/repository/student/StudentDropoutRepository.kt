package edu.watumull.presencify.core.domain.repository.student

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.model.student.Dropout

interface StudentDropoutRepository {
    suspend fun addStudentToDropout(studentId: String, academicStartYear: Int, academicEndYear: Int): Result<Dropout, DataError.Remote>
    suspend fun removeStudentFromDropout(studentId: String, academicStartYear: Int, academicEndYear: Int): Result<Unit, DataError.Remote>
    suspend fun getDropoutById(id: String): Result<Dropout, DataError.Remote>
    suspend fun getDropoutDetailsOfStudent(studentId: String): Result<List<Dropout>, DataError.Remote>
}