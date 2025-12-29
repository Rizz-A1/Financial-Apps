package com.rizkyfadilhanif.financial.data.repository

import com.rizkyfadilhanif.financial.data.local.dao.TransactionDao
import com.rizkyfadilhanif.financial.data.local.entity.TransactionEntity
import com.rizkyfadilhanif.financial.domain.model.Transaction
import com.rizkyfadilhanif.financial.domain.model.TransactionType
import com.rizkyfadilhanif.financial.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    
    override fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type.name).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getTransactionsByDateRange(startDate: Long, endDate: Long): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDateRange(startDate, endDate).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getTransactionsByTypeAndDateRange(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByTypeAndDateRange(type.name, startDate, endDate).map { list ->
            list.map { it.toDomain() }
        }
    }
    
    override fun getTotalByType(type: TransactionType): Flow<Double> {
        return transactionDao.getTotalByType(type.name)
    }
    
    override fun getTotalByTypeAndDateRange(
        type: TransactionType,
        startDate: Long,
        endDate: Long
    ): Flow<Double> {
        return transactionDao.getTotalByTypeAndDateRange(type.name, startDate, endDate)
    }
    
    override suspend fun getTransactionById(id: Long): Transaction? {
        return transactionDao.getTransactionById(id)?.toDomain()
    }
    
    override suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toEntity())
    }
    
    override suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }
    
    override suspend fun deleteTransaction(id: Long) {
        transactionDao.deleteTransactionById(id)
    }
    
    private fun TransactionEntity.toDomain(): Transaction {
        return Transaction(
            id = id,
            type = TransactionType.valueOf(type),
            amount = amount,
            description = description,
            category = category,
            date = date,
            createdAt = createdAt
        )
    }
    
    private fun Transaction.toEntity(): TransactionEntity {
        return TransactionEntity(
            id = id,
            type = type.name,
            amount = amount,
            description = description,
            category = category,
            date = date,
            createdAt = createdAt
        )
    }
}
