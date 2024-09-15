package com.atech.research

import android.app.Application
import com.atech.research.module.KoinInitializer
import com.google.firebase.FirebaseApp

class ResearchHub : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}