package com.atech.research.utils

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

suspend inline fun HttpClient.getWithFallback(
    crossinline block: HttpRequestBuilder.() -> Unit = {}
) =
    try {
        get(block = block)
    } catch (e: Exception) {
        get {
            header(HttpHeaders.CacheControl, "only-if-cached")
            block()
        }
    }
