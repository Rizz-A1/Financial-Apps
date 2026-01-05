package com.rizkyfadilhanif.financial.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_categories")
data class IncomeCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val targetAmount: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
