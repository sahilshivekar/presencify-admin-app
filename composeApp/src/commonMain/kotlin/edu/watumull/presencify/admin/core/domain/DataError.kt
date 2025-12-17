package edu.watumull.presencify.admin.core.domain


sealed interface DataError : Error {
    sealed interface Remote : DataError {
        data object REQUEST_TIMEOUT : Remote
        data object TOO_MANY_REQUESTS : Remote
        data object NO_INTERNET : Remote
        data object SERVER_ERROR : Remote
        data object SERIALIZATION : Remote
        data object UNKNOWN : Remote
        data object UNAUTHORIZED : Remote
        data class VALIDATION_ERROR(val message: String) : Remote
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}
