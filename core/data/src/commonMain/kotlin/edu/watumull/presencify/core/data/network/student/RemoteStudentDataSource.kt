package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.dto.student.StudentBatchDto
import edu.watumull.presencify.core.data.dto.student.StudentDivisionDto
import edu.watumull.presencify.core.data.dto.student.StudentDto
import edu.watumull.presencify.core.data.dto.student.StudentListWithTotalCountDto
import edu.watumull.presencify.core.data.dto.student.StudentSemesterDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.AdmissionType
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import kotlinx.datetime.LocalDate

interface RemoteStudentDataSource {
    suspend fun getStudents(
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
    ): Result<StudentListWithTotalCountDto, DataError.Remote>

    suspend fun addStudent(
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
        studentImage: ByteArray
    ): Result<StudentDto, DataError.Remote>

    suspend fun getStudentById(id: String): Result<StudentDto, DataError.Remote>

    suspend fun updateStudentDetails(
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
    ): Result<StudentDto, DataError.Remote>

    suspend fun updateStudentPassword(
        id: String,
        password: String,
        confirmPassword: String,
    ): Result<Unit, DataError.Remote>

    suspend fun updateStudentImage(
        id: String,
        imageBytes: ByteArray,
    ): Result<StudentDto, DataError.Remote>

    suspend fun removeStudentImage(studentId: String): Result<StudentDto, DataError.Remote>

    suspend fun removeStudent(id: String): Result<Unit, DataError.Remote>

    suspend fun getStudentSemesters(id: String): Result<List<StudentSemesterDto>, DataError.Remote>

    suspend fun addStudentToSemester(
        studentId: String,
        semesterId: String,
    ): Result<StudentSemesterDto, DataError.Remote>

    suspend fun removeStudentFromSemester(studentSemesterId: String): Result<Unit, DataError.Remote>

    suspend fun getStudentDivisions(
        id: String,
        semesterNumber: SemesterNumber?,
    ): Result<List<StudentDivisionDto>, DataError.Remote>

    suspend fun addStudentToDivision(
        studentId: String,
        divisionId: String,
    ): Result<StudentDivisionDto, DataError.Remote>

    suspend fun changeStudentDivision(
        studentDivisionId: String,
        divisionId: String,
        newDivisionStartDate: LocalDate,
    ): Result<StudentDivisionDto, DataError.Remote>

    suspend fun revertAddStudentToDivision(studentDivisionId: String): Result<Unit, DataError.Remote>

    suspend fun revertChangeStudentDivision(newStudentDivisionId: String): Result<Unit, DataError.Remote>

    suspend fun getStudentBatches(
        id: String,
        semesterNumber: SemesterNumber?,
    ): Result<List<StudentBatchDto>, DataError.Remote>

    suspend fun addStudentToBatch(
        studentId: String,
        batchId: String,
    ): Result<StudentBatchDto, DataError.Remote>

    suspend fun changeStudentBatch(
        studentBatchId: String,
        batchId: String,
        newBatchStartDate: LocalDate,
    ): Result<StudentBatchDto, DataError.Remote>

    suspend fun revertAddStudentToBatch(studentBatchId: String): Result<Unit, DataError.Remote>

    suspend fun revertChangeStudentBatch(newStudentBatchId: String): Result<Unit, DataError.Remote>

    suspend fun bulkCreateStudents(students: List<Map<String, Any?>>): Result<List<StudentDto>, DataError.Remote>

    suspend fun bulkDeleteStudents(studentIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkAddStudentsToSemester(
        studentIds: List<String>,
        semesterId: String,
    ): Result<List<StudentSemesterDto>, DataError.Remote>

    suspend fun bulkAddStudentsToDivision(
        studentIds: List<String>,
        divisionId: String,
    ): Result<List<StudentDivisionDto>, DataError.Remote>

    suspend fun bulkAddStudentsToBatch(
        studentIds: List<String>,
        batchId: String,
    ): Result<List<StudentBatchDto>, DataError.Remote>

    suspend fun bulkCreateStudentsFromCSV(csvData: ByteArray): Result<List<StudentDto>, DataError.Remote>
}
