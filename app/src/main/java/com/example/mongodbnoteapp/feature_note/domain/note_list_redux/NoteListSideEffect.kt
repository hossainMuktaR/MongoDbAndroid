package com.example.mongodbnoteapp.feature_note.domain.note_list_redux

import com.example.mongodbnoteapp.feature_note.redux.SideEffect

sealed class NoteListSideEffect: SideEffect {
    data class DeleteNote(val message: String): NoteListSideEffect()
    data class GoNoteEditScreen(val noteId: Int, val noteColor: Int): NoteListSideEffect()
    object NavigateAddNoteScreen: NoteListSideEffect()
}
