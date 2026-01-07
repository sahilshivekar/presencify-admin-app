package edu.watumull.presencify.core.domain.model.student

import kotlinx.datetime.LocalDate

data class Dropout(
    val id: String,
    val studentId: String,
    val reason: String?,
    val dropoutDate: LocalDate,
    val student: Student? = null
)
