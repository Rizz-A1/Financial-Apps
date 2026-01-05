package com.rizkyfadilhanif.financial.data.local.dao

import androidx.room.*
import com.rizkyfadilhanif.financial.data.local.entity.ExpenseCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Query("SELECT * FROM expense_categories ORDER BY createdAt DESC")
    fun getAllCategories(): Flow<List<ExpenseCategoryEntity>>
    
    @Query("SELECT * FROM expense_categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): ExpenseCategoryEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: ExpenseCategoryEntity): Long
    
    @Update
    suspend fun updateCategory(category: ExpenseCategoryEntity)
    
    @Delete
    suspend fun deleteCategory(category: ExpenseCategoryEntity)
    
    @Query("DELETE FROM expense_categories WHERE id = :id")
    suspend fun deleteCategoryById(id: Long)
}
