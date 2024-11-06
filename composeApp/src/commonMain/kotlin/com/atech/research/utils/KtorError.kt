package com.atech.research.utils

import com.atech.research.core.ktor.model.ErrorResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

/**
 * Check error
 * This function is used to check the error of the response
 * @param T The type of the data
 * @param R The type of the error
 * @param httpResponse The http response
 * @return DataState<T>
 * @see DataState
 */
suspend inline fun <reified T, reified R> checkError(
    httpResponse: HttpResponse,
): DataState<T> =
    when (httpResponse.status) {
        HttpStatusCode.OK -> DataState.Success(httpResponse.body<T>())

        HttpStatusCode.BadRequest ->
            DataState.Error(createException(httpResponse.body<R>()))


        HttpStatusCode.InternalServerError ->
            DataState.Error(createException(httpResponse.body<R>()))

        HttpStatusCode.NotFound ->
            DataState.Error(createException(httpResponse.body<R>()))

        else -> DataState.Error(createException(httpResponse.body<R>()))
    }

/**
 * Create exception
 * This function is used to create an exception
 * @param T The type of the exception
 * @param body The body
 * @return [Exception]
 */
inline fun <reified T> createException(body: T): Exception =
    when (body) {
        is String -> Exception(body)
        is Map<*, *> -> Exception(body.toString())
        is ErrorResponse -> Exception(body.error)
        else -> Exception("Unknown error")
    }



