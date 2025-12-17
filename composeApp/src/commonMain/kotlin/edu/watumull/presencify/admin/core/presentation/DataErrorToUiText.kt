package edu.watumull.presencify.admin.core.presentation

import edu.watumull.presencify.admin.core.domain.DataError
import presencifyadmin.composeapp.generated.resources.Res
import presencifyadmin.composeapp.generated.resources.error_disk_full
import presencifyadmin.composeapp.generated.resources.error_no_internet
import presencifyadmin.composeapp.generated.resources.error_serialization
import presencifyadmin.composeapp.generated.resources.error_unauthorized
import presencifyadmin.composeapp.generated.resources.error_unknown
import presencifyadmin.composeapp.generated.resources.server_error
import presencifyadmin.composeapp.generated.resources.the_request_timed_out
import presencifyadmin.composeapp.generated.resources.youve_hit_your_rate_limit

fun DataError.toUiText(): UiText {

    return when(this) {

        // Dynamic String Case
        is DataError.Remote.VALIDATION_ERROR -> {
            UiText.DynamicString(this.message)
        }

        // Resource ID Cases (Wrap them immediately)
        DataError.Local.DISK_FULL -> UiText.StringResourceId(Res.string.error_disk_full)
        DataError.Local.UNKNOWN -> UiText.StringResourceId(Res.string.error_unknown)
        DataError.Remote.REQUEST_TIMEOUT -> UiText.StringResourceId(Res.string.the_request_timed_out)
        DataError.Remote.TOO_MANY_REQUESTS -> UiText.StringResourceId(Res.string.youve_hit_your_rate_limit)
        DataError.Remote.NO_INTERNET -> UiText.StringResourceId(Res.string.error_no_internet)
        DataError.Remote.SERVER_ERROR -> UiText.StringResourceId(Res.string.server_error)
        DataError.Remote.SERIALIZATION -> UiText.StringResourceId(Res.string.error_serialization)
        DataError.Remote.UNKNOWN -> UiText.StringResourceId(Res.string.error_unknown)
        DataError.Remote.UNAUTHORIZED -> UiText.StringResourceId(Res.string.error_unauthorized)
    }
}