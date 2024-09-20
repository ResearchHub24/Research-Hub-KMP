package com.atech.research.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.atech.research.core.room.dao.ResearchDao
import com.atech.research.core.room.entities.ResearchCacheModel
import com.atech.research.core.room.entities.TagModelTypeConverter
import kotlinx.coroutines.Dispatchers

@Database(
    entities = [ResearchCacheModel::class],
    version = 1
)
@TypeConverters(
    TagModelTypeConverter::class
)
abstract class ResearchHubDatabase : RoomDatabase() {
    abstract fun researchDao(): ResearchDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<ResearchHubDatabase>
): ResearchHubDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(false)
        .build()
}