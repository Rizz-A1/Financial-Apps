package com.rizkyfadilhanif.financial.data.local.dao

import androidx.room.*
import com.rizkyfadilhanif.financial.data.local.entity.IncomeCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeCategoryDao {
    @Query("SELECT * FROM income_categories ORDER BY createdAt DESC")
    fun getAllCategories(): Flow<List<IncomeCategoryEntity>>
    
    @Query("SELECT * FROM income_categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): IncomeCategoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: IncomeCategoryEntity): Long
    
    @Update
    suspend fun updateCategory(category: IncomeCategoryEntity)
    
    @Delete
    suspend fun deleteCategory(category: IncomeCategoryEntity)
    
    @Query("DELETE FROM income_categories WHERE id = :id")
    suspend fun deleteCategoryById(id: Long)
}
