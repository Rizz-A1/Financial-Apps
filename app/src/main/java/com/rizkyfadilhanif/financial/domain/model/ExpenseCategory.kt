package com.rizkyfadilhanif.financial.domain.model

data class ExpenseCategory(
    val id: Long = 0,
    val name: String,
    val targetAmount: Double = 0.0,
    val currentAmount: Double = 0.0 // Calculated from transactions
)
