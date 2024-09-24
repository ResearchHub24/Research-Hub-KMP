package com.atech.research.module


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.atech.research.core.pref.createDataStore
import com.atech.research.getAppDataPath
import com.atech.research.ui.compose.login.compose.login.LogInViewModel
import com.atech.research.ui.compose.login.compose.login.LogInViewModelImp
import com.atech.research.ui.compose.login.compose.setup.SetUpViewModel
import com.atech.research.ui.compose.teacher.home.HomeScreenViewModel
import com.atech.research.utils.PrefManager
import com.atech.research.utils.PrefManager.Companion.PREF_NAME
import com.atech.research.utils.PrefManagerImp
import com.atech.research.utils.generateImageLoader
import org.koin.dsl.bind
import org.koin.dsl.module
import java.nio.file.Paths

val viewModelModule = module {
    single { LogInViewModelImp(get()) }.bind(LogInViewModel::class)

    single { SetUpViewModel(get()) }
    single<DataStore<Preferences>> {
        createDataStore {
            val appDataPath = getAppDataPath()
            Paths.get(appDataPath, PREF_NAME).toString()
        }
    }
    single {
        PrefManagerImp(get())
    }.bind(PrefManager::class)
    single { HomeScreenViewModel(get(), get()) }
    single { generateImageLoader() }

}