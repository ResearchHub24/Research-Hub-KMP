package com.atech.research.module

import com.atech.research.ui.compose.main.login.compose.login.LogInViewModel
import com.atech.research.ui.compose.main.login.compose.login.LogInViewModelImp
import com.atech.research.ui.compose.main.login.compose.setup.SetUpViewModel
import com.atech.research.ui.compose.main.login.compose.util.HasUserUseCase
import com.atech.research.ui.compose.main.login.compose.util.LogInUseCase
import com.atech.research.ui.compose.main.login.compose.util.LogInWithGoogleStudent
import com.atech.research.utils.createDataStore
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
    single { createDataStore(context = get()) }
}

val viewModelModule = module {
    viewModel { LogInViewModelImp(get()) }
        .bind(LogInViewModel::class)
    viewModel {
        SetUpViewModel(get())
    }
}