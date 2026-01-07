package edu.watumull.presencify.core.data.network.teacher_auth

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL

object ApiEndpoints {
    // Path segments
    const val TEACHER_AUTH = "auth/teachers"

    // Teacher authentication endpoints
    const val LOGIN_TEACHER = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHER_AUTH/login"
    const val SEND_VERIFICATION_CODE = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHER_AUTH/send-verification-code"
    const val VERIFY_CODE = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHER_AUTH/verify-code"
    const val UPDATE_PASSWORD = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHER_AUTH/update-password"
    const val REFRESH_TOKENS = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHER_AUTH/access-token"
    const val LOGOUT = "$PRESENCIFY_BASE_URL/$API_V1/$TEACHER_AUTH/logout"
}
