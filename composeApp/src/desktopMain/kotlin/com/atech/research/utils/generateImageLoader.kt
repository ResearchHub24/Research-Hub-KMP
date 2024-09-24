package com.atech.research.utils

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import okio.Path.Companion.toOkioPath
import java.io.File

fun generateImageLoader(): ImageLoader {
    return ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            // cache 32MB bitmap
            bitmapMemoryCacheConfig {
                maxSize(32 * 1024 * 1024) // 32MB
            }
            // cache 50 image
            imageMemoryCacheConfig {
                maxSize(50)
            }
            // cache 50 painter
            painterMemoryCacheConfig {
                maxSize(50)
            }
            diskCacheConfig {
                directory(getCacheDir().toOkioPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }
}

// about currentOperatingSystem, see app
fun getCacheDir(): File {
    val userHome = System.getProperty("user.home")
    val cachePath = when {
        System.getProperty("os.name").contains("Windows", ignoreCase = true) ->
            File(System.getenv("LOCALAPPDATA"), "ResearchHub/cache")

        System.getProperty("os.name").contains("Mac", ignoreCase = true) ->
            File(userHome, "Library/Caches/ResearchHub")

        else -> // Linux and other Unix-like systems
            File(userHome, ".cache/ResearchHub")
    }

    // Ensure the directory exists
    cachePath.mkdirs()

    return cachePath
}