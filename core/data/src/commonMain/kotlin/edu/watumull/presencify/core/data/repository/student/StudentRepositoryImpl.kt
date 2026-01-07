package edu.watumull.presencify.core.data.repository.student

import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.data.network.student.RemoteStudentDataSource
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import edu.watumull.presencify.core.domain.enums.AdmissionType
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.domain.enums.SemesterNumber
import edu.watumull.presencify.core.domain.map
import edu.watumull.presencify.core.domain.model.student.Student
import edu.watumull.presencify.core.domain.model.student.StudentBatch
import edu.watumull.presencify.core.domain.model.student.StudentDivision
import edu.watumull.presencify.core.domain.model.student.StudentSemester
import edu.watumull.presencify.core.domain.repository.student.StudentRepository
import kotlinx.datetime.LocalDate

class StudentRepositoryImpl(
    private val remoteDataSource: RemoteStudentDataSource,
) : StudentRepository {

    override suspend fun getStudents(
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
    ): Result<List<Student>, DataError.Remote> {
        return remoteDataSource.getStudents(
            searchQuery,
            branchIds,
            semesterNumbers,
            academicStartYearOfSemester,
            academicEndYearOfSemester,
            semesterId,
            batchId,
            schemeId,
            divisionId,
            dropoutAcademicStartYear,
            dropoutAcademicEndYear,
            admissionTypes,
            admissionYear,
            currentBatch,
            currentDivision,
            currentSemester,
            divisionCode,
            batchCode,
            page,
            limit,
            getAll
        ).map { response ->
            response.students.map { it.toDomain() }
        }
    }

    override suspend fun addStudent(
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
        studentImage: ByteArray,
    ): Result<Student, DataError.Remote> {
        return remoteDataSource.addStudent(
            prn,
            firstName,
            middleName,
            lastName,
            email,
            phoneNumber,
            gender,
            dob,
            schemeId,
            admissionYear,
            admissionType,
            branchId,
            parentEmail,
            studentImage
        ).map { it.toDomain() }
    }

    override suspend fun getStudentById(id: String): Result<Student, DataError.Remote> {
        return remoteDataSource.getStudentById(id).map { it.toDomain() }
    }

    override suspend fun updateStudentDetails(
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
    ): Result<Student, DataError.Remote> {
        return remoteDataSource.updateStudentDetails(
            id,
            firstName,
            middleName,
            lastName,
            email,
            gender,
            phoneNumber,
            dob,
            schemeId,
            branchId,
            parentEmail,
            prn,
            admissionYear,
            admissionType
        ).map { it.toDomain() }
    }

    override suspend fun updateStudentPassword(
        id: String,
        password: String,
        confirmPassword: String,
    ): Result<Unit, DataError.Remote> {
        return remoteDataSource.updateStudentPassword(id, password, confirmPassword)
    }

    override suspend fun updateStudentImage(
        id: String,
        studentImage: ByteArray,
    ): Result<Student, DataError.Remote> {
        return remoteDataSource.updateStudentImage(id, studentImage).map { it.toDomain() }
    }

    override suspend fun removeStudentImage(studentId: String): Result<Student, DataError.Remote> {
        return remoteDataSource.removeStudentImage(studentId).map { it.toDomain() }
    }

    override suspend fun removeStudent(id: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeStudent(id)
    }

    override suspend fun getStudentSemesters(id: String): Result<List<StudentSemester>, DataError.Remote> {
        return remoteDataSource.getStudentSemesters(id).map { semesters ->
            semesters.map { it.toDomain() }
        }
    }

    override suspend fun addStudentToSemester(
        studentId: String,
        semesterId: String,
    ): Result<StudentSemester, DataError.Remote> {
        return remoteDataSource.addStudentToSemester(studentId, semesterId).map { it.toDomain() }
    }

    override suspend fun removeStudentFromSemester(studentSemesterId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.removeStudentFromSemester(studentSemesterId)
    }

    override suspend fun getStudentDivisions(
        id: String,
        semesterNumber: SemesterNumber?,
    ): Result<List<StudentDivision>, DataError.Remote> {
        return remoteDataSource.getStudentDivisions(id, semesterNumber).map { divisions ->
            divisions.map { it.toDomain() }
        }
    }

    override suspend fun addStudentToDivision(
        studentId: String,
        divisionId: String,
    ): Result<StudentDivision, DataError.Remote> {
        return remoteDataSource.addStudentToDivision(studentId, divisionId).map { it.toDomain() }
    }

    override suspend fun changeStudentDivision(
        studentDivisionId: String,
        divisionId: String,
        newDivisionStartDate: LocalDate,
    ): Result<StudentDivision, DataError.Remote> {
        return remoteDataSource.changeStudentDivision(
            studentDivisionId,
            divisionId,
            newDivisionStartDate
        )
            .map { it.toDomain() }
    }

    override suspend fun revertAddStudentToDivision(studentDivisionId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.revertAddStudentToDivision(studentDivisionId)
    }

    override suspend fun revertChangeStudentDivision(newStudentDivisionId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.revertChangeStudentDivision(newStudentDivisionId)
    }

    override suspend fun getStudentBatches(
        id: String,
        semesterNumber: SemesterNumber?,
    ): Result<List<StudentBatch>, DataError.Remote> {
        return remoteDataSource.getStudentBatches(id, semesterNumber).map { batches ->
            batches.map { it.toDomain() }
        }
    }

    override suspend fun addStudentToBatch(
        studentId: String,
        batchId: String,
    ): Result<StudentBatch, DataError.Remote> {
        return remoteDataSource.addStudentToBatch(studentId, batchId).map { it.toDomain() }
    }

    override suspend fun changeStudentBatch(
        studentBatchId: String,
        batchId: String,
        newBatchStartDate: LocalDate,
    ): Result<StudentBatch, DataError.Remote> {
        return remoteDataSource.changeStudentBatch(studentBatchId, batchId, newBatchStartDate)
            .map { it.toDomain() }
    }

    override suspend fun revertAddStudentToBatch(studentBatchId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.revertAddStudentToBatch(studentBatchId)
    }

    override suspend fun revertChangeStudentBatch(newStudentBatchId: String): Result<Unit, DataError.Remote> {
        return remoteDataSource.revertChangeStudentBatch(newStudentBatchId)
    }

    override suspend fun bulkCreateStudents(students: List<Map<String, Any?>>): Result<List<Student>, DataError.Remote> {
        return remoteDataSource.bulkCreateStudents(students).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkDeleteStudents(studentIds: List<String>): Result<Unit, DataError.Remote> {
        return remoteDataSource.bulkDeleteStudents(studentIds)
    }

    override suspend fun bulkAddStudentsToSemester(
        studentIds: List<String>,
        semesterId: String,
    ): Result<List<StudentSemester>, DataError.Remote> {
        return remoteDataSource.bulkAddStudentsToSemester(studentIds, semesterId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkAddStudentsToDivision(
        studentIds: List<String>,
        divisionId: String,
    ): Result<List<StudentDivision>, DataError.Remote> {
        return remoteDataSource.bulkAddStudentsToDivision(studentIds, divisionId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkAddStudentsToBatch(
        studentIds: List<String>,
        batchId: String,
    ): Result<List<StudentBatch>, DataError.Remote> {
        return remoteDataSource.bulkAddStudentsToBatch(studentIds, batchId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun bulkCreateStudentsFromCSV(csvData: ByteArray): Result<List<Student>, DataError.Remote> {
        return remoteDataSource.bulkCreateStudentsFromCSV(csvData).map { list ->
            list.map { it.toDomain() }
        }
    }
}
