package com.atech.research.utils

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

/**
 * Get with fallback
 * This function is used to get the data with fallback
 * @receiver HttpClient
 * @param block The block
 * @return [String]
 */
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
