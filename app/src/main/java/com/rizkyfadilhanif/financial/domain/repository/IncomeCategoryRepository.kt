package com.rizkyfadilhanif.financial.domain.repository

import com.rizkyfadilhanif.financial.domain.model.IncomeCategory
import kotlinx.coroutines.flow.Flow

interface IncomeCategoryRepository {
    fun getAllCategories(): Flow<List<IncomeCategory>>
    suspend fun getCategoryById(id: Long): IncomeCategory?
    suspend fun insertCategory(category: IncomeCategory): Long
    suspend fun updateCategory(category: IncomeCategory)
    suspend fun deleteCategory(id: Long)
}
