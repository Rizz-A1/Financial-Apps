package com.rizkyfadilhanif.financial.data.repository

import com.rizkyfadilhanif.financial.data.local.dao.NoteDao
import com.rizkyfadilhanif.financial.data.local.entity.NoteEntity
import com.rizkyfadilhanif.financial.domain.model.Note
import com.rizkyfadilhanif.financial.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {
    
    override fun getNotesByType(type: String): Flow<List<Note>> {
        return noteDao.getNotesByType(type).map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { it.toDomain() }
        }
    }
    
    override suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)?.toDomain()
    }
    
    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note.toEntity())
    }
    
    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }
    
    override suspend fun deleteNote(id: Long) {
        noteDao.deleteNoteById(id)
    }
    
    private fun NoteEntity.toDomain(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            type = type
        )
    }
    
    private fun Note.toEntity(): NoteEntity {
        return NoteEntity(
            id = id,
            title = title,
            content = content,
            type = type
        )
    }
}
