package edu.watumull.presencify.core.data.mapper.academics

import edu.watumull.presencify.core.data.dto.academics.BatchDto
import edu.watumull.presencify.core.data.mapper.schedule.toDomain
import edu.watumull.presencify.core.data.mapper.student.toDomain
import edu.watumull.presencify.core.domain.model.academics.Batch

fun BatchDto.toDomain(): Batch = Batch(
    id = id,
    batchCode = batchCode,
    divisionId = divisionId,
    division = division?.toDomain(),
    studentBatches = studentBatches?.map { it.toDomain() },
    classes = classes?.map { it.toDomain() }
)
