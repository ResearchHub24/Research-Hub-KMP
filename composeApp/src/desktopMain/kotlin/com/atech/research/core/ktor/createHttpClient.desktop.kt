package com.atech.research.core.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.io.File
import java.nio.file.Paths

class DesktopClientEngineFactory : EngineFactory {
    override fun createEngine(): HttpClient {
        return createHttpClient(
            OkHttp.create(),
            GetCacheDir()
        )
    }
}


actual fun httpClientEngineFactory(): EngineFactory = DesktopClientEngineFactory()


actual class GetCacheDir {

    actual fun getCacheDir(): File {
        val userHome = System.getProperty("user.home")
        val appDataPath = when {
            System.getProperty("os.name").contains("Windows", ignoreCase = true) -> Paths.get(
                System.getenv("APPDATA"),
                "ResearchHub",
                "caches"
            ).toString()

            System.getProperty("os.name").contains("Mac", ignoreCase = true) -> Paths.get(
                userHome,
                "Library",
                "Application Support",
                "ResearchHub",
                "caches"
            ).toString()

            else -> // Linux and other Unix-like systems
                Paths.get(userHome, ".config", "ResearchHub", "caches").toString()
        }
        return File(appDataPath)
    }

}