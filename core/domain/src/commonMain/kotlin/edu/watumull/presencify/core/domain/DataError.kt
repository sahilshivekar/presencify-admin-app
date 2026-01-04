package edu.watumull.presencify.core.domain


sealed interface DataError : Error {
    sealed interface Remote : DataError {
        data object RequestTimeout : Remote
        data object TooManyRequests : Remote
        data object NoInternet : Remote
        data object ServerError : Remote
        data object Serialization : Remote
        data object Unknown : Remote
        data object Unauthorized : Remote
        data object Forbidden : Remote
        data class CustomError(val message: String) : Remote
    }

    sealed interface Local : DataError {
        data object DiskFull
        data object Unknown : Local
    }
}
