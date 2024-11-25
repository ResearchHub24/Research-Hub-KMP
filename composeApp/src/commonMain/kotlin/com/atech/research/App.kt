package com.atech.research

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.atech.research.core.ktor.model.UserType
import com.atech.research.ui.navigation.ResearchHubNavigation
import com.atech.research.ui.navigation.ResearchNavigationGraph
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.isAndroid
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.LocalImageLoader
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

val LocalDataStore = staticCompositionLocalOf<PrefManager> { error("No DataStore provided") }

/**
 * App
 * Main entry point for the app.
 *
 * @param pref PrefManager
 * @param navHostController NavHostController
 * @see PrefManager
 * @see NavHostController
 */
@Composable
@Preview
fun App(
    pref: PrefManager,
    navHostController: NavHostController = rememberNavController()
) {
//    setSingletonImageLoaderFactory { context ->
//        getAsyncImageLoader(context)
//    }
    ResearchHubTheme(
        isAndroid = isAndroid()
    ) {
        val imageLoad = koinInject<ImageLoader>()
        CompositionLocalProvider(
            LocalDataStore provides pref,
            LocalImageLoader provides remember { imageLoad }) {
            val startDestination = if (pref.getString(Prefs.USER_ID.name)
                    .isNotBlank() && pref.getBoolean(Prefs.SET_PASSWORD_DONE.name)
            ) {
                if (pref.getString(Prefs.USER_TYPE.name).isNotBlank() &&
                    pref.getString(Prefs.USER_TYPE.name) == UserType.STUDENT.name
                ) ResearchHubNavigation.MainScreen
                else ResearchHubNavigation.VerifyScreen
            }
            else ResearchHubNavigation.LogInScreen

            ResearchNavigationGraph(
                modifier = Modifier,
                navHostController = navHostController,
                startDestination = startDestination,
            )
        }
    }
}

//fun getAsyncImageLoader(context: PlatformContext) =
//    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
//        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
//    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
//        newDiskCache()
//    }.crossfade(true).logger(DebugLogger()).build()
//
//fun newDiskCache(): DiskCache {
//    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
//        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
//        .build()
//}
