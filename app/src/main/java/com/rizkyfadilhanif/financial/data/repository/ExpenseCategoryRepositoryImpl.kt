package com.rizkyfadilhanif.financial.data.repository

import com.rizkyfadilhanif.financial.data.local.dao.ExpenseCategoryDao
import com.rizkyfadilhanif.financial.data.local.entity.ExpenseCategoryEntity
import com.rizkyfadilhanif.financial.domain.model.ExpenseCategory
import com.rizkyfadilhanif.financial.domain.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseCategoryRepositoryImpl(
    private val expenseCategoryDao: ExpenseCategoryDao
) : ExpenseCategoryRepository {
    
    override fun getAllCategories(): Flow<List<ExpenseCategory>> {
        return expenseCategoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getCategoryById(id: Long): ExpenseCategory? {
        return expenseCategoryDao.getCategoryById(id)?.toDomain()
    }
    
    override suspend fun insertCategory(category: ExpenseCategory): Long {
        return expenseCategoryDao.insertCategory(category.toEntity())
    }
    
    override suspend fun updateCategory(category: ExpenseCategory) {
        expenseCategoryDao.updateCategory(category.toEntity())
    }
    
    override suspend fun deleteCategory(id: Long) {
        expenseCategoryDao.deleteCategoryById(id)
    }
    
    private fun ExpenseCategoryEntity.toDomain(): ExpenseCategory {
        return ExpenseCategory(
            id = id,
            name = name,
            targetAmount = targetAmount
        )
    }
    
    private fun ExpenseCategory.toEntity(): ExpenseCategoryEntity {
        return ExpenseCategoryEntity(
            id = id,
            name = name,
            targetAmount = targetAmount
        )
    }
}
