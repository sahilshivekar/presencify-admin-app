package edu.watumull.presencify.core.data.dto.academics

import edu.watumull.presencify.core.data.dto.schedule.ClassDto
import edu.watumull.presencify.core.data.dto.student.StudentBatchDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BatchDto(
    val id: String,
    val batchCode: String,
    val divisionId: String,
    val createdAt: String,
    @SerialName("Division")
    val division: DivisionDto? = null,
    @SerialName("StudentBatches")
    val studentBatches: List<StudentBatchDto>? = null,
    @SerialName("Classes")
    val classes: List<ClassDto>? = null
)
