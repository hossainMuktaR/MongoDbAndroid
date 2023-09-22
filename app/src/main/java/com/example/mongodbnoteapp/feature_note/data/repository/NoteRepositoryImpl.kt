package com.example.mongodbnoteapp.feature_note.data.repository

import com.example.mongodbnoteapp.feature_note.data.data_source.NoteDao
import com.example.mongodbnoteapp.feature_note.domain.model.Note
import com.example.mongodbnoteapp.feature_note.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun getAllNotes(): List<Note> = noteDao.getAllNotes()

    override suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id)

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note.toNoteDto())

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note.toNoteDto())

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note.toNoteDto())

}