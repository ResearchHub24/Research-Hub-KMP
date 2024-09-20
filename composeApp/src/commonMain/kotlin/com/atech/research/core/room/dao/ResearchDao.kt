package com.atech.research.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.atech.research.core.room.entities.ResearchCacheModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ResearchDao {
    @Query("SELECT * FROM research_cache ORDER BY created DESC")
    fun getAll(): Flow<List<ResearchCacheModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(researchCacheModel: ResearchCacheModel)

    @Upsert
    suspend fun upsert(researchCacheModel: ResearchCacheModel)

    @Query("DELETE FROM research_cache")
    suspend fun deleteAll()

    @Query("SELECT * FROM research_cache WHERE path = :path")
    suspend fun getResearchByPath(path: String): ResearchCacheModel?


}