package edu.watumull.presencify.core.domain.model.schedule

import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import edu.watumull.presencify.core.domain.model.academics.Batch
import edu.watumull.presencify.core.domain.model.academics.Course
import edu.watumull.presencify.core.domain.model.attendance.Attendance
import edu.watumull.presencify.core.domain.model.teacher.Teacher
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class ClassSession(
    val id: String,
    val teacherId: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val dayOfWeek: DayOfWeek,
    val roomId: String,
    val batchId: String?,
    val activeFrom: LocalDate,
    val activeTill: LocalDate,
    val classType: ClassType,
    val courseId: String,
    val timetableId: String,
    val isExtraClass: Boolean,
    val teacher: Teacher? = null,
    val room: Room? = null,
    val batch: Batch? = null,
    val course: Course? = null,
    val timetable: Timetable? = null,
    val attendances: List<Attendance>? = null
)
