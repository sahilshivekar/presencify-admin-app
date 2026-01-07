package edu.watumull.presencify.core.data.mapper.schedule

import edu.watumull.presencify.core.data.dto.schedule.CancelledClassDto
import edu.watumull.presencify.core.domain.model.schedule.CancelledClass

fun CancelledClassDto.toDomain(): CancelledClass = CancelledClass(
    id = id,
    classId = classId,
    cancelDate = cancelDate,
    reason = reason,
    klass = klass?.toDomain()
)
