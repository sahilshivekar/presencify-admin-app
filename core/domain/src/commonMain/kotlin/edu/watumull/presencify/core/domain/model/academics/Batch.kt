package edu.watumull.presencify.core.domain.model.academics

import edu.watumull.presencify.core.domain.DisplayLabelProvider
import edu.watumull.presencify.core.domain.model.schedule.ClassSession
import edu.watumull.presencify.core.domain.model.student.StudentBatch

data class Batch(
    val id: String,
    val batchCode: String,
    val divisionId: String,
    val division: Division? = null,
    val studentBatches: List<StudentBatch>? = null,
    val classes: List<ClassSession>? = null
) : DisplayLabelProvider {

    override fun toDisplayLabel(): String = batchCode
}
