package edu.watumull.presencify.core.data.network.admin_auth

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.ADMINS
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.AUTH
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL

object ApiEndpoints {
    // Admin auth endpoints
    const val LOGIN = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/login"
    const val SEND_VERIFICATION_CODE_FORGOT = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/forgot-password"
    const val VERIFY_CODE = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/verify-code"
    const val REFRESH_TOKENS = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/access-token"
    const val VERIFY_PASSWORD = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/verify-password"
    const val UPDATE_ADMIN_PASSWORD = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/update-password"
    const val LOGOUT = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/logout"
    const val SEND_VERIFICATION_CODE_EMAIL = "$PRESENCIFY_BASE_URL/$API_V1/$AUTH/$ADMINS/email-verification"
}