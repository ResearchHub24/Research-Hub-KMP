package com.atech.research.module


import com.atech.research.core.pref.DATA_STORE_FILE_NAME
import com.atech.research.core.pref.createDataStore
import com.atech.research.getAppDataPath
import com.atech.research.ui.compose.login.compose.login.LogInViewModel
import com.atech.research.ui.compose.login.compose.login.LogInViewModelImp
import com.atech.research.ui.compose.login.compose.setup.SetUpViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import java.nio.file.Paths

val viewModelModule = module {
    single { LogInViewModelImp(get()) }
        .bind(LogInViewModel::class)

    single { SetUpViewModel(get()) }
    single {
        createDataStore {
            val appDataPath = getAppDataPath()
            Paths.get(appDataPath, DATA_STORE_FILE_NAME).toString()
        }
    }
}