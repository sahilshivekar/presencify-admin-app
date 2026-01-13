package edu.watumull.presencify.core.presentation.validation

import edu.watumull.presencify.core.presentation.utils.DateTimeUtils
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun LocalDate?.validateAsAttendanceDate(): ValidationResult {
    if (this == null) {
        return ValidationResult(successful = false, errorMessage = "This field is required")
    }
    val today = DateTimeUtils.getCurrentDate()

    if (this > today) {
        return ValidationResult(successful = false, errorMessage = "Date cannot be in the future")
    }

    return ValidationResult(successful = true)
}