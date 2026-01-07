package edu.watumull.presencify.core.data.network.student

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.IMAGE
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PASSWORD
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.STUDENTS

object ApiEndpoints {
    // Independent Routes (Mounted at root in app.js)
    const val DROPOUTS_ROOT = "dropouts"
    const val STUDENT_FCM_TOKENS_ROOT = "student-fcm-tokens"

    // Student endpoints
    const val GET_STUDENTS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS"
    const val ADD_STUDENT = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS"
    const val GET_STUDENT_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS" // Usage: /$id
    const val UPDATE_STUDENT_DETAILS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS" // Usage: /$id
    const val UPDATE_STUDENT_PASSWORD = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/$PASSWORD"
    const val UPDATE_STUDENT_IMAGE = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/$IMAGE"
    const val REMOVE_STUDENT_IMAGE = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/$IMAGE"
    const val REMOVE_STUDENT = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS" // Usage: /$id

    // Student relationship endpoints
    // Note: Backend uses /students/:id/semesters for GET, but /students/semester for POST/DELETE
    const val GET_STUDENT_SEMESTERS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS" // Usage: /$id/semesters
    const val ADD_STUDENT_TO_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/semester"
    const val REMOVE_STUDENT_FROM_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/semester"

    const val GET_STUDENT_DIVISIONS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS" // Usage: /$id/divisions
    const val ADD_STUDENT_TO_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/division"
    const val CHANGE_STUDENT_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/division"
    const val REVERT_ADD_STUDENT_TO_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/division/revert-add"
    const val REVERT_CHANGE_STUDENT_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/division/revert-change"

    const val GET_STUDENT_BATCHES = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS" // Usage: /$id/batches
    const val ADD_STUDENT_TO_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/batch"
    const val CHANGE_STUDENT_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/batch"
    const val REVERT_ADD_STUDENT_TO_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/batch/revert-add"
    const val REVERT_CHANGE_STUDENT_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/batch/revert-change"

    // Bulk operations
    const val BULK_CREATE_STUDENTS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/bulk/create"
    const val BULK_DELETE_STUDENTS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/bulk/delete"
    const val BULK_ADD_STUDENTS_TO_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/bulk/semester"
    const val BULK_ADD_STUDENTS_TO_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/bulk/division"
    const val BULK_ADD_STUDENTS_TO_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/bulk/batch"
    const val BULK_CREATE_STUDENTS_FROM_CSV = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENTS/bulk/csv"

    // Dropout endpoints (Mounted at /api/v1/dropouts in app.js)
    const val ADD_STUDENT_TO_DROPOUT = "$PRESENCIFY_BASE_URL/$API_V1/$DROPOUTS_ROOT"
    const val REMOVE_STUDENT_FROM_DROPOUT = "$PRESENCIFY_BASE_URL/$API_V1/$DROPOUTS_ROOT"
    const val GET_DROPOUT_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$DROPOUTS_ROOT" // Usage: /$id
    const val GET_DROPOUT_DETAILS_OF_STUDENT = "$PRESENCIFY_BASE_URL/$API_V1/$DROPOUTS_ROOT/student"

    // FCM Token endpoints (Mounted at /api/v1/student-fcm-tokens in app.js)
    const val ADD_STUDENT_FCM_TOKENS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENT_FCM_TOKENS_ROOT"
    const val UPDATE_STUDENT_FCM_TOKENS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENT_FCM_TOKENS_ROOT"
    const val REMOVE_STUDENT_FCM_TOKENS = "$PRESENCIFY_BASE_URL/$API_V1/$STUDENT_FCM_TOKENS_ROOT"
}