package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.dto.student.DropoutDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result

interface RemoteDropoutDataSource {
    suspend fun addStudentToDropout(studentId: String, academicStartYear: Int, academicEndYear: Int): Result<DropoutDto, DataError.Remote>
    suspend fun removeStudentFromDropout(studentId: String, academicStartYear: Int, academicEndYear: Int): Result<Unit, DataError.Remote>
    suspend fun getDropoutById(id: String): Result<DropoutDto, DataError.Remote>
    suspend fun getDropoutDetailsOfStudent(studentId: String): Result<List<DropoutDto>, DataError.Remote>
}