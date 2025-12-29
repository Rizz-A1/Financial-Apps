package com.rizkyfadilhanif.financial.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: String, // "INCOME" or "EXPENSE"
    val amount: Double,
    val description: String,
    val category: String,
    val date: Long, // timestamp
    val createdAt: Long = System.currentTimeMillis()
)
