package com.atech.research.notification

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.atech.research.R
import com.atech.research.utils.FacultyNotification
import com.atech.research.utils.MessageNotification
import com.atech.research.utils.ResearchNotification
import com.atech.research.utils.Topics
import com.atech.research.utils.convertToInt
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val TAG = "ResearchHub"

class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(TAG, "onMessageReceived: Message Received")
        createNotice(message)
    }

    private fun createNotice(message: RemoteMessage) {
        val getChannel = if (message.from?.contains("/topics/") == true) {
            if (message.from?.extractTopic() == Topics.ResearchPosted.name)
                ResearchNotification().notificationChannelId
            else
                FacultyNotification().notificationChannelId
        } else
            MessageNotification().notificationChannelId

        val builder = NotificationCompat.Builder(this, getChannel)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.notification?.title ?: "")
            .setContentText(message.notification?.body ?: "").setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val key = message.data["created"]?.toLong()?.convertToInt() ?: Random.nextInt()
        val researchPath = message.data["key"] // Todo : Open research detail page
        Log.d("AAA", "$researchPath , $key, ${message.from}")
        val managerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        managerCompat.notify(key, builder.build())
    }

    private fun String.extractTopic() = this.replace("/topics/", "")
}