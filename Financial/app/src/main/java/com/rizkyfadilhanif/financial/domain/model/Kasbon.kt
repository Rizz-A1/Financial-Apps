package com.rizkyfadilhanif.financial.domain.model

data class Kasbon(
    val id: Long = 0,
    val employeeId: Long,
    val employeeName: String = "",
    val amount: Double,
    val description: String,
    val isPaid: Boolean = false,
    val date: Long,
    val paidDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
