package com.rizkyfadilhanif.financial.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "kasbon",
    foreignKeys = [
        ForeignKey(
            entity = EmployeeEntity::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class KasbonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val employeeId: Long,
    val amount: Double,
    val description: String,
    val isPaid: Boolean = false,
    val date: Long,
    val paidDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
