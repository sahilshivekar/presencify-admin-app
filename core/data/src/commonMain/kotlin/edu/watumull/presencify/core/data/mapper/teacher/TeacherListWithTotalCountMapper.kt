package edu.watumull.presencify.core.data.mapper.teacher

import edu.watumull.presencify.core.data.dto.teacher.TeacherListWithTotalCountDto
import edu.watumull.presencify.core.domain.model.teacher.TeacherListWithTotalCount

fun TeacherListWithTotalCountDto.toDomain(): TeacherListWithTotalCount = TeacherListWithTotalCount(
    teachers = teacher.map { it.toDomain() },
    totalTeacher = totalTeacher
)
