package com.atech.research.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi


interface NotificationModel {
    val notificationChannelId: String
    val notificationChannelName: String
    val notificationChannelDescription: String
}

@RequiresApi(Build.VERSION_CODES.O)
fun NotificationModel.createNotificationChannel(context: Context) {
    val noticeChannel = NotificationChannel(
        this.notificationChannelId,
        this.notificationChannelName,
        NotificationManager.IMPORTANCE_HIGH
    )
    noticeChannel.description = this.notificationChannelDescription
    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(noticeChannel)
}

data class FacultyNotification(
    override val notificationChannelId: String = "faculty",
    override val notificationChannelName: String = "Faculty",
    override val notificationChannelDescription: String = """
        Notification for Faculty
        Generally send by organization
    """.trimIndent()
) : NotificationModel

data class ResearchNotification(
    override val notificationChannelId: String = "research",
    override val notificationChannelName: String = "Research",
    override val notificationChannelDescription: String = """
        Stay updated with latest research publication
    """.trimIndent()
) : NotificationModel


data class MessageNotification(
    override val notificationChannelId: String = "message",
    override val notificationChannelName: String = "Personal Messages",
    override val notificationChannelDescription: String = """
        Notification related to personal dm
    """.trimIndent()
) : NotificationModel

fun Long.convertToInt(): Int = (this and 0xFFFFFFFF).toInt()