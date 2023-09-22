package com.example.mongodbnoteapp.feature_note.domain.add_edit_note_redux

import com.example.mongodbnoteapp.feature_note.domain.use_case.NoteUseCases
import com.example.mongodbnoteapp.feature_note.presentation.add_edit_note.AddEditNoteState
import com.example.mongodbnoteapp.feature_note.redux.BaseContainer

class AddEditNoteContainer(
    useCases: NoteUseCases
): BaseContainer<AddEditNoteAction, AddEditNoteState, AddEditNoteSideEffect> (
    initialState = AddEditNoteState(),
    reducer = AddEditNoteReducer(),
    middleware = listOf(
        AddEditEffectMiddleware(),
        NoteSaveMiddleware(useCases)
    ),
)