package com.example.mongodbnoteapp.feature_note.domain.add_edit_note_redux

import com.example.mongodbnoteapp.feature_note.domain.use_case.NoteUseCases
import com.example.mongodbnoteapp.feature_note.presentation.add_edit_note.AddEditNoteState
import com.example.mongodbnoteapp.feature_note.redux.BaseContainer
import kotlinx.coroutines.CoroutineScope

class AddEditNoteContainer(
    useCases: NoteUseCases,
    scope: CoroutineScope
): BaseContainer<AddEditNoteAction, AddEditNoteState, AddEditNoteSideEffect> (
    initialState = AddEditNoteState(),
    scope = scope,
    reducer = AddEditNoteReducer(),
    middleware = listOf(
        AddEditEffectMiddleware(),
        NoteSaveMiddleware(useCases)
    ),
)