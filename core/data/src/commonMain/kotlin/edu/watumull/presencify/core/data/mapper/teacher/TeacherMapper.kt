package edu.watumull.presencify.core.data.mapper.teacher

import edu.watumull.presencify.core.data.dto.teacher.TeacherDto
import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.domain.model.teacher.Teacher

fun TeacherDto.toDomain(): Teacher = Teacher(
    id = id,
    firstName = firstName,
    middleName = middleName,
    lastName = lastName,
    teacherImageUrl = teacherImageUrl,
    teacherImagePublicId = teacherImagePublicId,
    email = email,
    phoneNumber = phoneNumber,
    gender = gender,
    highestQualification = highestQualification,
    role = role,
    password = password,
    isActive = isActive,
    refreshToken = refreshToken,
    classes = classes?.map { it.toDomain() },
    teacherTeachesCourses = teacherTeachesCourses?.map { it.toDomain() }
)
