package com.atech.research.module

import com.atech.research.ui.compose.main.login.compose.LogInViewModel
import com.atech.research.ui.compose.main.login.compose.LogInViewModelImp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val appModule = module {
    single<FirebaseAuth> {
        Firebase.auth
    }
}

val viewModelModule = module {
    viewModel { LogInViewModelImp(get()) }
        .bind(LogInViewModel::class)
}