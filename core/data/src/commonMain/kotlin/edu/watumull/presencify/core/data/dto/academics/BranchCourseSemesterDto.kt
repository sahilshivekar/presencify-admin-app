package edu.watumull.presencify.core.data.dto.academics

import edu.watumull.presencify.core.domain.enums.SemesterNumber
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BranchCourseSemesterDto(
    val id: String,
    val branchId: String,
    val courseId: String,
    val semesterNumber: SemesterNumber,
    val createdAt: String,
    val updatedAt: String,
    @SerialName("Branch")
    val branch: BranchDto? = null,
    @SerialName("Course")
    val course: CourseDto? = null
)
