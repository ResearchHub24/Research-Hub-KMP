package com.atech.research.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.File


interface EngineFactory {
    fun createEngine(): HttpClient
}

expect fun httpClientEngineFactory(): EngineFactory

expect class GetCacheDir {
    fun getCacheDir(): File
}

fun createHttpClient(
    engine: HttpClientEngine,
    cacheDir: GetCacheDir
): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(HttpCache) {
            val dir = cacheDir.getCacheDir()
            publicStorage(FileStorage(dir))
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
        install(HttpRequestRetry) {
            maxRetries = 3
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }

    }
}