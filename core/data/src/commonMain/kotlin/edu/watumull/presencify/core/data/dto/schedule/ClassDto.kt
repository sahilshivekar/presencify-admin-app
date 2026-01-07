package edu.watumull.presencify.core.data.dto.schedule

import edu.watumull.presencify.core.data.dto.academics.BatchDto
import edu.watumull.presencify.core.data.dto.academics.CourseDto
import edu.watumull.presencify.core.data.dto.attendance.AttendanceDto
import edu.watumull.presencify.core.data.dto.teacher.TeacherDto
import edu.watumull.presencify.core.domain.enums.ClassType
import edu.watumull.presencify.core.domain.enums.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassDto(
    val id: String,
    val teacherId: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val dayOfWeek: DayOfWeek,
    val roomId: String,
    val batchId: String? = null,
    val activeFrom: String,
    val activeTill: String,
    val classType: ClassType,
    val courseId: String,
    val createdAt: String,
    val updatedAt: String,
    val timetableId: String,
    val isExtraClass: Boolean = false,
    @SerialName("Teacher")
    val teacher: TeacherDto? = null,
    @SerialName("Room")
    val room: RoomDto? = null,
    @SerialName("Batch")
    val batch: BatchDto? = null,
    @SerialName("Course")
    val course: CourseDto? = null,
    @SerialName("Timetable")
    val timetable: TimetableDto? = null,
    @SerialName("Attendances")
    val attendances: List<AttendanceDto>? = null
)
