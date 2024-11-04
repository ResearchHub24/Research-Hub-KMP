package com.atech.research.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import com.atech.research.MainActivity
import com.atech.research.R
import com.atech.research.utils.DeepLink
import com.atech.research.utils.ResearchNotification
import com.atech.research.utils.convertToInt
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val TAG = "ResearchHub"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        createNotice(message)
    }

    private fun createNotice(message: RemoteMessage) {
//        val getChannel = if (message.from?.contains("/topics/") == true) {
//            if (message.from?.extractTopic() == Topics.ResearchPosted.name)
//                ResearchNotification().notificationChannelId
//            else
//                FacultyNotification().notificationChannelId
//        } else
//            MessageNotification().notificationChannelId

        val key = message.data["created"]?.toLong()?.convertToInt() ?: Random.nextInt()
        val researchPath = message.data["key"] ?: "no_research"
        val pendingIntent = providePendingIntentNotice(researchPath)
        val builder = NotificationCompat.Builder(this, ResearchNotification().notificationChannelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(message.notification?.title ?: "")
            .setContentText(message.notification?.body ?: "").setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.notification?.body ?: ""))
            .setGroup("research_hub")
            .setGroupSummary(true)
            .build()
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        NotificationManagerCompat.from(this)
            .notify(key, builder)
    }


    private fun providePendingIntentNotice(path: String): PendingIntent? {
        val deepLinkIntent = Intent(
            Intent.ACTION_VIEW,
            DeepLink.OpenResearch(researchPath = path).route.toUri(),
            this,
            MainActivity::class.java
        )
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("from_notification", true)
                putExtra("research_path", path)
            }

        return PendingIntent.getActivity(
            this,
            0,
            deepLinkIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun getPendingIntentFlag() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        else
            PendingIntent.FLAG_UPDATE_CURRENT

    private fun String.extractTopic() = this.replace("/topics/", "")
}