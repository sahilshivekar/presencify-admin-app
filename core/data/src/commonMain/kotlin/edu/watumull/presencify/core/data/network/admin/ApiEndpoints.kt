package edu.watumull.presencify.core.data.network.admin

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.ADMINS
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL

object ApiEndpoints {
    // Path segments
    const val ME = "me"

    // Admin endpoints
    const val ADD_ADMIN = "$PRESENCIFY_BASE_URL/$API_V1/$ADMINS"
    const val UPDATE_ADMIN_DETAILS = "$PRESENCIFY_BASE_URL/$API_V1/$ADMINS/$ME"
    const val REMOVE_ADMIN = "$PRESENCIFY_BASE_URL/$API_V1/$ADMINS/$ME"
    const val GET_ADMINS = "$PRESENCIFY_BASE_URL/$API_V1/$ADMINS"
    const val GET_ADMIN_DETAILS = "$PRESENCIFY_BASE_URL/$API_V1/$ADMINS/$ME"
}