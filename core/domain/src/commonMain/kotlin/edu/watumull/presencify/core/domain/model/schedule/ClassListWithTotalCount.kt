package edu.watumull.presencify.core.domain.model.schedule

data class ClassListWithTotalCount(
    val classes: List<ClassSession>,
    val totalCount: Int
)