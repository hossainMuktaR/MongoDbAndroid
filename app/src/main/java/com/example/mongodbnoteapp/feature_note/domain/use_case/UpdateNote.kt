package com.example.mongodbnoteapp.feature_note.domain.use_case

import com.example.mongodbnoteapp.feature_note.domain.model.InvalidNoteException
import com.example.mongodbnoteapp.feature_note.domain.model.Note
import com.example.mongodbnoteapp.feature_note.domain.repository.NoteRepository

class UpdateNote (
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note are empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note are empty")
        }
        repository.updateNote(note)
    }
}