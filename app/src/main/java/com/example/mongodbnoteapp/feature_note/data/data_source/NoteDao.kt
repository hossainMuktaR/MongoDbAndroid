package com.example.mongodbnoteapp.feature_note.data.data_source

import com.example.mongodbnoteapp.feature_note.data.NoteDto
import com.example.mongodbnoteapp.feature_note.domain.model.Note

interface NoteDao {

    suspend fun getAllNotes(): List<Note>
    suspend fun getNoteById(noteId: Int): Note?
    suspend fun insertNote(noteDto: NoteDto)
    suspend fun updateNote(noteDto: NoteDto)
    suspend fun deleteNote(noteDto: NoteDto)

}