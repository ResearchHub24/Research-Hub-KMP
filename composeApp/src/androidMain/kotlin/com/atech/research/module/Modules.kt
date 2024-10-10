package com.atech.research.module

import android.content.Context
import android.content.SharedPreferences
import com.atech.research.core.ktor.AndroidClientEngineFactory
import com.atech.research.core.ktor.EngineFactory
import com.atech.research.ui.compose.login.compose.login.LogInViewModel
import com.atech.research.ui.compose.login.compose.login.LogInViewModelImp
import com.atech.research.ui.compose.login.compose.setup.SetUpViewModel
import com.atech.research.ui.compose.login.compose.util.HasUserUseCase
import com.atech.research.ui.compose.login.compose.util.LogInUseCase
import com.atech.research.ui.compose.login.compose.util.LogInWithGoogleStudent
import com.atech.research.ui.compose.login.compose.util.SignOutUseCase
import com.atech.research.ui.compose.profile.ProfileViewModel
import com.atech.research.ui.compose.profile.SignOutHelper
import com.atech.research.ui.compose.teacher.home.HomeScreenViewModel
import com.atech.research.utils.LinkHelper
import com.atech.research.utils.PrefManager
import com.atech.research.utils.PrefManager.Companion.PREF_NAME
import com.atech.research.utils.PrefManagerImp
import com.atech.research.utils.generateImageLoader
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single<FirebaseAuth> {
        Firebase.auth
    }
    single<FirebaseFirestore> {
        Firebase.firestore
    }
    single { HasUserUseCase(get()) }
    single { LogInUseCase(get(), get()) }
    single { LogInWithGoogleStudent(get(), get()) }
    single { SignOutUseCase(get()) }
//    single { getRoomDatabase(getDatabaseBuilder(get())) }
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }
    single { PrefManagerImp(get()) }.bind(PrefManager::class)
    single {
        AndroidClientEngineFactory(get())

    }.bind(EngineFactory::class)
    single { generateImageLoader(get()) }

    single { LinkHelper(get()) }

    single { SignOutHelper(get(), get()) }
}

val viewModelModule = module {
    viewModel { LogInViewModelImp(get()) }
        .bind(LogInViewModel::class)
    viewModel {
        SetUpViewModel(get())
    }
    viewModel {
        HomeScreenViewModel(get(), get())
    }
    viewModel { ProfileViewModel(get(), get(), get()) }
}