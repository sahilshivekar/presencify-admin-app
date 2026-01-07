package edu.watumull.presencify.core.domain.model.student

import edu.watumull.presencify.core.domain.model.academics.Batch
import kotlinx.datetime.LocalDate

data class StudentBatch(
    val id: String,
    val studentId: String,
    val batchId: String,
    val fromDate: LocalDate,
    val toDate: LocalDate?,
    val student: Student? = null,
    val batch: Batch? = null
)
