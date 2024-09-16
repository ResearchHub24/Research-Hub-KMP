package com.atech.research.module


import com.atech.research.core.pref.DATA_STORE_FILE_NAME
import com.atech.research.core.pref.createDataStore
import com.atech.research.getAppDataPath
import com.atech.research.ui.compose.main.login.compose.login.LogInViewModel
import com.atech.research.ui.compose.main.login.compose.login.LogInViewModelImp
import com.atech.research.ui.compose.main.login.compose.setup.SetUpViewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import java.nio.file.Paths

val viewModelModule = module {
    single { LogInViewModelImp() }
        .bind(LogInViewModel::class)

    single { SetUpViewModel() }
    single {
        createDataStore {
            val appDataPath = getAppDataPath()
            Paths.get(appDataPath, DATA_STORE_FILE_NAME).toString()
        }
    }
}