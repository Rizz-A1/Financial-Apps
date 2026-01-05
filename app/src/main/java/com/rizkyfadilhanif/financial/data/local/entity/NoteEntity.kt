package com.rizkyfadilhanif.financial.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val type: String = "INCOME", // INCOME or EXPENSE
    val createdAt: Long = System.currentTimeMillis()
)
