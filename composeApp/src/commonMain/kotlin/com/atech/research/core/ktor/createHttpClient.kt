package com.atech.research.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Engine factory
 * This is a factory to create a HttpClient engine
 * This is needed because the HttpClient engine is platform specific
 * and we need to create it in the platform specific code
 * This is a common interface that will be implemented in the platform specific code
 * and will be used in the common code
 * @constructor Create empty Engine factory
 */
interface EngineFactory {
    /**
     * Create engine
     * This function will create a HttpClient engine
     *
     * @return [HttpClient]
     */
    fun createEngine(): HttpClient
}

/**
 * Http client engine factory
 * This function will return the engine factory
 * This function is platform specific and will be implemented in the platform specific code
 *
 * @return [EngineFactory]
 * @see EngineFactory
 */
expect fun httpClientEngineFactory(): EngineFactory

/**
 * Get cache dir
 * This is a common interface that will be implemented in the platform specific code
 * and will be used in the common code
 * This is needed because the cache directory is platform specific
 * and we need to get it in the platform specific code
 * @constructor Create empty Get cache dir
 */
expect class GetCacheDir {
    /**
     * Get cache dir
     * This function will return the cache directory
     * @return [File]
     */
    fun getCacheDir(): File
}

/**
 * Create http client
 * This function will create a HttpClient with the given engine and cache directory
 * @param engine The HttpClient engine [HttpClientEngine]
 * @param cacheDir The cache directory [GetCacheDir]
 * @return [HttpClient]
 */
fun createHttpClient(
    engine: HttpClientEngine,
    cacheDir: GetCacheDir
): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
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
        install(DefaultRequest) {
            header(HttpHeaders.CacheControl, "no-cache")
        }

    }
}