package edu.watumull.presencify.core.presentation.validation

import edu.watumull.presencify.core.domain.enums.AdmissionType
import edu.watumull.presencify.core.domain.enums.Gender
import edu.watumull.presencify.core.presentation.utils.DateTimeUtils
import kotlinx.datetime.LocalDate

@Suppress("DEPRECATION")
fun LocalDate?.validateAsDob(): ValidationResult {
    // Optional field
    if (this == null) return ValidationResult(successful = true)

    val today = DateTimeUtils.getCurrentDate()

    if (this >= today) {
        return ValidationResult(successful = false, errorMessage = "Date of birth must be in the past")
    }

    return ValidationResult(successful = true)
}

fun String.validateAsPrn(): ValidationResult {
    val required = ValidationRule.Required().validate(this)
    if (!required.successful) return required

    val maxLength = ValidationRule.MaxLength(100).validate(this)
    if (!maxLength.successful) return maxLength

    return ValidationResult(successful = true)
}

fun Gender?.validateAsGender(): ValidationResult {
    if (this == null) {
        return ValidationResult(successful = false, errorMessage = "Gender must be selected")
    }
    return ValidationResult(successful = true)
}

fun AdmissionType?.validateAsAdmissionType(): ValidationResult {
    if (this == null) {
        return ValidationResult(successful = false, errorMessage = "Admission type must be selected")
    }
    return ValidationResult(successful = true)
}

fun String?.validateAsAdmissionYear(): ValidationResult {
    if (this.isNullOrBlank()) {
        return ValidationResult(successful = false, errorMessage = "Admission year cannot be empty")
    }

    val year = this.toIntOrNull() ?: return ValidationResult(
        successful = false,
        errorMessage = "Admission year must be a valid number"
    )

    if (year !in 1900..3000) {
        return ValidationResult(successful = false, errorMessage = "Admission year must be between 1900 and 3000")
    }

    return ValidationResult(successful = true)
}