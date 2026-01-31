package edu.watumull.presencify.core.presentation.validation

import edu.watumull.presencify.core.domain.model.academics.Course

fun String.validateAsCourseCode(): ValidationResult {
    val required = ValidationRule.Required().validate(this)
    if (!required.successful) return required

    val maxLength = ValidationRule.MaxLength(255).validate(this)
    if (!maxLength.successful) return maxLength

    return ValidationResult(successful = true)
}

fun String.validateAsCourseName(): ValidationResult {
    val required = ValidationRule.Required().validate(this)
    if (!required.successful) return required

    val maxLength = ValidationRule.MaxLength(255).validate(this)
    if (!maxLength.successful) return maxLength

    return ValidationResult(successful = true)
}

fun String.validateAsOptionalCourse(): ValidationResult {
    // Optional field
    if (this.isBlank()) return ValidationResult(successful = true)

    val maxLength = ValidationRule.MaxLength(255).validate(this)
    if (!maxLength.successful) return maxLength

    return ValidationResult(successful = true)
}

fun Course?.validateAsCourse(): ValidationResult {
    if (this == null) {
        return ValidationResult(successful = false, errorMessage = "Course must be selected")
    }
    return ValidationResult(successful = true)
}