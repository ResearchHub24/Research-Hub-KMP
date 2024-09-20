package com.atech.research.core.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(
    context: Context
): RoomDatabase.Builder<ResearchHubDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("research_hub.db")
    return Room.databaseBuilder(
        appContext,
        ResearchHubDatabase::class.java,
        dbFile.absolutePath
    )
}