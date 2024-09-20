package com.atech.research.core.ktor

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.context.GlobalContext.get
import java.io.File


class AndroidClientEngineFactory(
    private val context: Context
) : EngineFactory {
    override fun createEngine(): HttpClient {
        return createHttpClient(
            OkHttp.create(),
            GetCacheDir(context)
        )
    }
}


actual fun httpClientEngineFactory(): EngineFactory {
    val androidClientEngineFactory: EngineFactory = get().get()
    return androidClientEngineFactory
}

actual class GetCacheDir(private val context: Context) {
    actual fun getCacheDir(): File = context.cacheDir

}