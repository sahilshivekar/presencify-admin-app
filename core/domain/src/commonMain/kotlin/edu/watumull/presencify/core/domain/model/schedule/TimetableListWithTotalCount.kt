package edu.watumull.presencify.core.domain.model.schedule

data class TimetableListWithTotalCount(
    val timetables: List<Timetable>,
    val totalCount: Int
)