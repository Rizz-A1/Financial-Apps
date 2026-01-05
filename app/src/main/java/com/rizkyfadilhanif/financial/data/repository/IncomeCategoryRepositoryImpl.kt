package com.rizkyfadilhanif.financial.data.repository

import com.rizkyfadilhanif.financial.data.local.dao.IncomeCategoryDao
import com.rizkyfadilhanif.financial.data.local.entity.IncomeCategoryEntity
import com.rizkyfadilhanif.financial.domain.model.IncomeCategory
import com.rizkyfadilhanif.financial.domain.repository.IncomeCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IncomeCategoryRepositoryImpl(
    private val incomeCategoryDao: IncomeCategoryDao
) : IncomeCategoryRepository {
    
    override fun getAllCategories(): Flow<List<IncomeCategory>> {
        return incomeCategoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getCategoryById(id: Long): IncomeCategory? {
        return incomeCategoryDao.getCategoryById(id)?.toDomain()
    }
    
    override suspend fun insertCategory(category: IncomeCategory): Long {
        return incomeCategoryDao.insertCategory(category.toEntity())
    }
    
    override suspend fun updateCategory(category: IncomeCategory) {
        incomeCategoryDao.updateCategory(category.toEntity())
    }
    
    override suspend fun deleteCategory(id: Long) {
        incomeCategoryDao.deleteCategoryById(id)
    }
    
    private fun IncomeCategoryEntity.toDomain(): IncomeCategory {
        return IncomeCategory(
            id = id,
            name = name,
            targetAmount = targetAmount
        )
    }
    
    private fun IncomeCategory.toEntity(): IncomeCategoryEntity {
        return IncomeCategoryEntity(
            id = id,
            name = name,
            targetAmount = targetAmount
        )
    }
}
