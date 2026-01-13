package edu.watumull.presencify.core.presentation

import edu.watumull.presencify.core.domain.DataError
import presencify.core.presentation.generated.resources.*

fun DataError.toUiText(): UiText {

    return when(this) {
        // Dynamic String Case
        is DataError.Remote.BusinessLogicError -> {
            UiText.DynamicString(this.message)
        }
        // Resource ID Cases
        DataError.Local.DiskFull -> UiText.StringResourceId(Res.string.error_storage_full)
        DataError.Local.Unknown -> UiText.StringResourceId(Res.string.error_unexpected)
        DataError.Remote.RequestTimeout -> UiText.StringResourceId(Res.string.error_request_timeout)
        DataError.Remote.TooManyRequests -> UiText.StringResourceId(Res.string.error_rate_limit_exceeded)
        DataError.Remote.NoInternet -> UiText.StringResourceId(Res.string.error_no_internet_connection)
        DataError.Remote.ServerError -> UiText.StringResourceId(Res.string.error_server)
        DataError.Remote.Serialization -> UiText.StringResourceId(Res.string.error_data_processing)
        DataError.Remote.Unknown -> UiText.StringResourceId(Res.string.error_unexpected)
        DataError.Remote.Unauthorized -> UiText.StringResourceId(Res.string.error_access_denied)
        DataError.Remote.Forbidden -> UiText.StringResourceId(Res.string.error_access_forbidden)
    }
}