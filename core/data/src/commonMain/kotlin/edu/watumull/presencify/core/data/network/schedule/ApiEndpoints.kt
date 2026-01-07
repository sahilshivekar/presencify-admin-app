package edu.watumull.presencify.core.data.network.schedule

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.CANCELLED
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.CLASSES
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.ROOMS
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.TIMETABLES

object ApiEndpoints {

    // Class endpoints
    const val GET_CLASSES = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES"
    const val ADD_CLASS = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES"
    const val GET_CLASS_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES"
    const val EXTEND_ACTIVE_TILL_DATE = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES"
    const val REMOVE_CLASS = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES"
    const val ADD_EXTRA_CLASS = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES/extra"
    const val GET_CANCELLED_CLASSES = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES/$CANCELLED"
    const val CANCEL_CLASS = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES/$CANCELLED"
    const val BULK_CREATE_CLASSES = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES/bulk"
    const val BULK_DELETE_CLASSES = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES/bulk"
    const val BULK_CREATE_CLASSES_FROM_CSV = "$PRESENCIFY_BASE_URL/$API_V1/$CLASSES/bulk/csv"

    // Room endpoints
    const val GET_ROOMS = "$PRESENCIFY_BASE_URL/$API_V1/$ROOMS"
    const val ADD_ROOM = "$PRESENCIFY_BASE_URL/$API_V1/$ROOMS"
    const val GET_ROOM_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$ROOMS"
    const val GET_ROOM_SCHEDULE = "$PRESENCIFY_BASE_URL/$API_V1/$ROOMS"
    const val UPDATE_ROOM = "$PRESENCIFY_BASE_URL/$API_V1/$ROOMS"
    const val REMOVE_ROOM = "$PRESENCIFY_BASE_URL/$API_V1/$ROOMS"

    // Timetable endpoints
    const val GET_TIMETABLES = "$PRESENCIFY_BASE_URL/$API_V1/$TIMETABLES"
    const val GET_TIMETABLE_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$TIMETABLES"
    const val ADD_TIMETABLE = "$PRESENCIFY_BASE_URL/$API_V1/$TIMETABLES"
    const val UPDATE_TIMETABLE = "$PRESENCIFY_BASE_URL/$API_V1/$TIMETABLES"
    const val REMOVE_TIMETABLE = "$PRESENCIFY_BASE_URL/$API_V1/$TIMETABLES"
}
