package edu.watumull.presencify.core.domain.repository.student

import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.AdmissionType
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.model.student.Student
import edu.watumull.presencify.core.domain.model.student.StudentBatch
import edu.watumull.presencify.core.domain.model.student.StudentDivision
import edu.watumull.presencify.core.domain.model.student.StudentSemester
import kotlinx.datetime.LocalDate

interface StudentRepository {
    suspend fun getStudents(
        searchQuery: String? = null,
        branchIds: List<String>? = null,
        semesterNumbers: List<Int>? = null,
        academicStartYearOfSemester: Int? = null,
        academicEndYearOfSemester: Int? = null,
        semesterId: String? = null,
        batchId: String? = null,
        schemeId: String? = null,
        divisionId: String? = null,
        dropoutAcademicStartYear: Int? = null,
        dropoutAcademicEndYear: Int? = null,
        admissionTypes: List<AdmissionType>? = null,
        admissionYear: Int? = null,
        currentBatch: Boolean? = null,
        currentDivision: Boolean? = null,
        currentSemester: Boolean? = null,
        divisionCode: String? = null,
        batchCode: String? = null,
        page: Int? = null,
        limit: Int? = null,
        getAll: Boolean? = null
    ): Result<List<Student>, DataError.Remote>

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
    ): Result<Student, DataError.Remote>

    suspend fun getStudentById(id: String): Result<Student, DataError.Remote>

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
        admissionType: AdmissionType?
    ): Result<Student, DataError.Remote>

    suspend fun updateStudentPassword(id: String, password: String, confirmPassword: String): Result<Unit, DataError.Remote>

    suspend fun updateStudentImage(id: String, studentImage: ByteArray): Result<Student, DataError.Remote>

    suspend fun removeStudentImage(studentId: String): Result<Student, DataError.Remote>

    suspend fun removeStudent(id: String): Result<Unit, DataError.Remote>

    // Semester operations
    suspend fun getStudentSemesters(id: String): Result<List<StudentSemester>, DataError.Remote>

    suspend fun addStudentToSemester(
        studentId: String,
        semesterId: String
    ): Result<StudentSemester, DataError.Remote>

    suspend fun removeStudentFromSemester(studentSemesterId: String): Result<Unit, DataError.Remote>

    // Division operations
    suspend fun getStudentDivisions(id: String, semesterNumber: SemesterNumber?): Result<List<StudentDivision>, DataError.Remote>

    suspend fun addStudentToDivision(
        studentId: String,
        divisionId: String
    ): Result<StudentDivision, DataError.Remote>

    suspend fun changeStudentDivision(
        studentDivisionId: String,
        divisionId: String,
        newDivisionStartDate: LocalDate
    ): Result<StudentDivision, DataError.Remote>

    suspend fun revertAddStudentToDivision(studentDivisionId: String): Result<Unit, DataError.Remote>

    suspend fun revertChangeStudentDivision(newStudentDivisionId: String): Result<Unit, DataError.Remote>

    // Batch operations
    suspend fun getStudentBatches(id: String, semesterNumber: SemesterNumber?): Result<List<StudentBatch>, DataError.Remote>

    suspend fun addStudentToBatch(
        studentId: String,
        batchId: String
    ): Result<StudentBatch, DataError.Remote>

    suspend fun changeStudentBatch(
        studentBatchId: String,
        batchId: String,
        newBatchStartDate: LocalDate
    ): Result<StudentBatch, DataError.Remote>

    suspend fun revertAddStudentToBatch(studentBatchId: String): Result<Unit, DataError.Remote>

    suspend fun revertChangeStudentBatch(newStudentBatchId: String): Result<Unit, DataError.Remote>

    // Bulk operations
    suspend fun bulkCreateStudents(students: List<Map<String, Any?>>): Result<List<Student>, DataError.Remote>

    suspend fun bulkDeleteStudents(studentIds: List<String>): Result<Unit, DataError.Remote>

    suspend fun bulkAddStudentsToSemester(studentIds: List<String>, semesterId: String): Result<List<StudentSemester>, DataError.Remote>

    suspend fun bulkAddStudentsToDivision(studentIds: List<String>, divisionId: String): Result<List<StudentDivision>, DataError.Remote>

    suspend fun bulkAddStudentsToBatch(studentIds: List<String>, batchId: String): Result<List<StudentBatch>, DataError.Remote>

    suspend fun bulkCreateStudentsFromCSV(csvData: ByteArray): Result<List<Student>, DataError.Remote>
}
