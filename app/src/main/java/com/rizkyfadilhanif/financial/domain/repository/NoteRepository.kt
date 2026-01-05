package com.rizkyfadilhanif.financial.domain.repository

import com.rizkyfadilhanif.financial.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesByType(type: String): Flow<List<Note>>
    fun getAllNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
}
