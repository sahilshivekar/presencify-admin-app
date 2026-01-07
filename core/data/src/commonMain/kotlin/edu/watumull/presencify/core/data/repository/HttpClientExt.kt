package edu.watumull.presencify.core.data.repository

import edu.watumull.presencify.core.data.dto.ApiResponseDto
import edu.watumull.presencify.core.domain.DataError
import edu.watumull.presencify.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.io.IOException

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse,
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: IOException) { // Covers SocketTimeoutException and UnresolvedAddressException
        return Result.Error(DataError.Remote.NoInternet)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        return Result.Error(DataError.Remote.Unknown)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse,
): Result<T, DataError.Remote> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                val apiResponse = response.body<ApiResponseDto<T>>()
                Result.Success(apiResponse.data!!)
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.Remote.Serialization)
            }
        }

        400 -> Result.Error(
            DataError.Remote.CustomError(
                message = response.body<ApiResponseDto<T>>().message
            )
        )

        401 -> Result.Error(DataError.Remote.Unauthorized)
        403 -> Result.Error(DataError.Remote.Forbidden)
        408 -> Result.Error(DataError.Remote.RequestTimeout)
        429 -> Result.Error(DataError.Remote.TooManyRequests)
        in 500..599 -> Result.Error(DataError.Remote.ServerError)
        else -> Result.Error(DataError.Remote.Unknown)
    }
}
