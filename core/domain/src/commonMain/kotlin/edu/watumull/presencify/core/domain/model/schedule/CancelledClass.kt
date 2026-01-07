package edu.watumull.presencify.core.domain.model.schedule

import kotlinx.datetime.LocalDate

data class CancelledClass(
    val id: String,
    val classId: String,
    val cancelDate: LocalDate,
    val reason: String?,
    val klass: ClassSession?
)