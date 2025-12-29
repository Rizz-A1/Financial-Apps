package com.rizkyfadilhanif.financial.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val position: String,
    val salary: Double,
    val phone: String,
    val createdAt: Long = System.currentTimeMillis()
)
