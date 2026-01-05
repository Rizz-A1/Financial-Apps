package com.rizkyfadilhanif.financial.domain.repository

import com.rizkyfadilhanif.financial.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseCategoryRepository {
    fun getAllCategories(): Flow<List<ExpenseCategory>>
    suspend fun getCategoryById(id: Long): ExpenseCategory?
    suspend fun insertCategory(category: ExpenseCategory): Long
    suspend fun updateCategory(category: ExpenseCategory)
    suspend fun deleteCategory(id: Long)
}
