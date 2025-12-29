package com.rizkyfadilhanif.financial.domain.model

data class Transaction(
    val id: Long = 0,
    val type: TransactionType,
    val amount: Double,
    val description: String,
    val category: String,
    val date: Long,
    val createdAt: Long = System.currentTimeMillis()
)
