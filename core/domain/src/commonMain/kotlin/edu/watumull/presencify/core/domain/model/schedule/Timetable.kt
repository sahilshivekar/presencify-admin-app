package edu.watumull.presencify.core.domain.model.schedule

import edu.watumull.presencify.core.domain.model.academics.Division

data class Timetable(
    val id: String,
    val divisionId: String,
    val timetableVersion: Int,
    val division: Division? = null,
    val classes: List<ClassSession>? = null
)
