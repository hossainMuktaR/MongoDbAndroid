package com.example.mongodbnoteapp.feature_note.domain.add_edit_note_redux

import com.example.mongodbnoteapp.feature_note.presentation.add_edit_note.AddEditNoteState
import com.example.mongodbnoteapp.feature_note.redux.Container
import com.example.mongodbnoteapp.feature_note.redux.Middleware

class AddEditEffectMiddleware: Middleware<AddEditNoteAction, AddEditNoteState, AddEditNoteSideEffect> {
    override suspend fun process(
        action: AddEditNoteAction,
        currentState: AddEditNoteState,
        container: Container<AddEditNoteAction, AddEditNoteState, AddEditNoteSideEffect>
    ) {
        when(action) {

            else -> { }
        }
    }
}
