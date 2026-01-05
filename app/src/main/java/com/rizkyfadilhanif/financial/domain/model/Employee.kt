package com.rizkyfadilhanif.financial.domain.model

data class Employee(
    val id: Long = 0,
    val name: String,
    val position: String,
    val salary: Double,
    val phone: String,
    val address: String = "",
    val age: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
