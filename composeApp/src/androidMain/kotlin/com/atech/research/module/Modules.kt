package com.atech.research.module

import android.content.Context
import android.content.SharedPreferences
import com.atech.research.core.ktor.AndroidClientEngineFactory
import com.atech.research.core.ktor.EngineFactory
import com.atech.research.ui.compose.forum.ForumViewModel
import com.atech.research.ui.compose.login.compose.login.LogInViewModel
import com.atech.research.ui.compose.login.compose.login.LogInViewModelImp
import com.atech.research.ui.compose.login.compose.setup.SetUpViewModel
import com.atech.research.ui.compose.login.compose.util.HasUserUseCase
import com.atech.research.ui.compose.login.compose.util.LogInUseCase
import com.atech.research.ui.compose.login.compose.util.LogInWithGoogleStudent
import com.atech.research.ui.compose.login.compose.util.SignOutUseCase
import com.atech.research.ui.compose.login.compose.verify.VerifyScreenViewModel
import com.atech.research.ui.compose.profile.ProfileViewModel
import com.atech.research.ui.compose.profile.SignOutHelper
import com.atech.research.ui.compose.student.application.ApplicationViewModel
import com.atech.research.ui.compose.student.faculties.FacultiesViewModel
import com.atech.research.ui.compose.student.home.StudentHomeViewModel
import com.atech.research.ui.compose.teacher.application.ResearchApplicationsViewModel
import com.atech.research.ui.compose.teacher.home.HomeScreenViewModel
import com.atech.research.utils.LinkHelper
import com.atech.research.utils.PrefManager
import com.atech.research.utils.PrefManager.Companion.PREF_NAME
import com.atech.research.utils.PrefManagerImp
import com.atech.research.utils.Toast
import com.atech.research.utils.generateImageLoader
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging
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
    single<FirebaseMessaging> { Firebase.messaging }
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
    single { Toast(get()) }
}


val viewModelModule = module {
    viewModel { LogInViewModelImp(get()) }
        .bind(LogInViewModel::class)
    viewModel {
        SetUpViewModel(get())
    }
    viewModel {
        HomeScreenViewModel(get(), get(), get())
    }
    viewModel { ProfileViewModel(get(), get(), get(), get()) }
    viewModel { StudentHomeViewModel(get(), get(), get(), get()) }
    viewModel { FacultiesViewModel(get()) }
    viewModel { ApplicationViewModel(get(), get()) }
    viewModel { ResearchApplicationsViewModel(get(), get(), get()) }
    viewModel { ForumViewModel(get(), get()) }
    viewModel { VerifyScreenViewModel(get(), get()) }

}