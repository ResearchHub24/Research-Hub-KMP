package com.atech.research.utils

import com.atech.research.core.ktor.model.ErrorResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

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


inline fun <reified T> createException(body: T): Exception =
    when (body) {
        is String -> Exception(body)
        is Map<*, *> -> Exception(body.toString())
        is ErrorResponse -> Exception(body.error)
        else -> Exception("Unknown error")
    }



