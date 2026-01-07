package edu.watumull.presencify.core.data.network.teacher

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.IMAGE
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PASSWORD
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.SUBJECTS
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.TEACHERS

object ApiEndpoints {

    // Teacher endpoints
    const val GET_TEACHERS = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS"
    const val ADD_TEACHER = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS"
    const val GET_TEACHER_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS"
    const val UPDATE_TEACHER_DETAILS = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS"
    const val REMOVE_TEACHER = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS"

    const val UPDATE_TEACHER_PASSWORD = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/$PASSWORD"
    const val UPDATE_TEACHER_IMAGE = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/$IMAGE"
    const val REMOVE_TEACHER_IMAGE = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/$IMAGE"

    // Teaching subjects endpoints
    const val GET_TEACHING_SUBJECTS = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/$SUBJECTS"
    const val ADD_TEACHING_SUBJECT = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/$SUBJECTS"
    const val REMOVE_TEACHING_SUBJECT = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/$SUBJECTS"

    // Bulk operations
    const val BULK_CREATE_TEACHERS = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/bulk/create"
    const val BULK_DELETE_TEACHERS = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/bulk/delete"
    const val BULK_CREATE_TEACHERS_FROM_CSV = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHERS/bulk/csv"
}