package com.atech.research.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp


class AndroidClientEngineFactory : EngineFactory {
    override fun createEngine(): HttpClient {
        return createHttpClient(
            OkHttp.create()
        )
    }
}


actual fun httpClientEngineFactory(): EngineFactory = AndroidClientEngineFactory()