package com.atech.research.core.room

import androidx.room.Room
import androidx.room.RoomDatabase
import com.atech.research.getAppDataPath
import java.io.File

fun getDatabaseBuilder(
): RoomDatabase.Builder<ResearchHubDatabase> {
    val dbFile = File(getAppDataPath(), "research_hub.db")
    return Room.databaseBuilder<ResearchHubDatabase>(
        name = dbFile.absolutePath,
    )
}