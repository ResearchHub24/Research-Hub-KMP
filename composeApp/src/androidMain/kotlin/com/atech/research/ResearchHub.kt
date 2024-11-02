package com.atech.research

import android.app.Application
import android.os.Build
import com.atech.research.core.ktor.model.UserType
import com.atech.research.module.KoinInitializer
import com.atech.research.utils.FacultyNotification
import com.atech.research.utils.MessageNotification
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchNotification
import com.atech.research.utils.Topics
import com.atech.research.utils.createNotificationChannel
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject

class ResearchHub : Application() {
    private val pref: PrefManager by inject()
    private val fcm: FirebaseMessaging by inject()
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
        if (!isTeacher()) {
            fcm.subscribeToTopic(Topics.ResearchPosted.name)
        } else {
            fcm.unsubscribeFromTopic(Topics.ResearchPosted.name)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MessageNotification().createNotificationChannel(this)
            if (!isTeacher())
                ResearchNotification().createNotificationChannel(this)
            else
                FacultyNotification().createNotificationChannel(this)
        }
    }


    private fun isTeacher() = pref.getString(Prefs.USER_TYPE.name) == UserType.TEACHER.name

}