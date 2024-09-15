package com.atech.research.module


import com.atech.research.ui.compose.main.login.compose.LogInViewModel
import com.atech.research.ui.compose.main.login.compose.LogInViewModelImp
import org.koin.dsl.bind
import org.koin.dsl.module

val viewModelModule = module {
    single { LogInViewModelImp() }
        .bind(LogInViewModel::class)
}