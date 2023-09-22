package com.example.mongodbnoteapp.feature_note.domain.use_case

data class NoteUseCases(
    val getAllNotes: GetAllNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val updateNote: UpdateNote,
    val getNoteById: GetNoteById,
)
