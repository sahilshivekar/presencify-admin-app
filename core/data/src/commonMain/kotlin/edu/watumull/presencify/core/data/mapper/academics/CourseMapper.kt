package edu.watumull.presencify.core.data.mapper.academics

import edu.watumull.presencify.core.data.dto.academics.CourseDto
import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.data.mapper.teacher.toDomain
import edu.watumull.presencify.core.domain.model.academics.Course

fun CourseDto.toDomain(): Course = Course(
    id = id,
    schemeId = schemeId,
    code = code,
    name = name,
    optionalCourse = optionalCourse,
    scheme = scheme?.toDomain(),
    branchCourseSemesters = branchCourseSemesters?.map { it.toDomain() },
    classes = classes?.map { it.toDomain() },
    teacherTeachesCourses = teacherTeachesCourses?.map { it.toDomain() }
)
