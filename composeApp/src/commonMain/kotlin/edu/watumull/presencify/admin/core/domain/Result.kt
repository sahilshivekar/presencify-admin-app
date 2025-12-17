package edu.watumull.presencify.admin.core.domain

sealed interface Result<out D, out E: DataError> {

    data class Success<out D>(
        val data: D
    ) : Result<D, Nothing>

    data class Error<out E: DataError>(
        val error: E
    ): Result<Nothing, E>
    
}
