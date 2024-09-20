package com.atech.research.core.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "research_cache")
data class ResearchCacheModel(
    @PrimaryKey(autoGenerate = false)
    val path: String,
    val title: String,
    val description: String,
    val author: String,
    val authorUid: String,
    val tags: List<TagCacheModel> = emptyList(),
    val created: Long = System.currentTimeMillis(),
)

data class TagCacheModel(
    val created: Long = System.currentTimeMillis(),
    val createdBy: String,
    val name: String,
)

object TagModelTypeConverter {
    @TypeConverter
    @JvmStatic
    fun fromTagModel(tagCacheModel: List<TagCacheModel>): String =
        Json.encodeToString(tagCacheModel)


    @TypeConverter
    @JvmStatic
    fun toTagModel(tagModelString: String): List<TagCacheModel> =
        Json.decodeFromString(tagModelString)
}