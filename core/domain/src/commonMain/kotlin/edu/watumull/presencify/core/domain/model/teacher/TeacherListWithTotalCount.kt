package edu.watumull.presencify.core.domain.model.teacher

data class TeacherListWithTotalCount(
    val teachers: List<Teacher>,
    val totalTeacher: Int
)