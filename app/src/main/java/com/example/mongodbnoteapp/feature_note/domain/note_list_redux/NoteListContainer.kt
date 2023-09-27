package com.example.mongodbnoteapp.feature_note.domain.note_list_redux

import com.example.mongodbnoteapp.feature_note.domain.use_case.NoteUseCases
import com.example.mongodbnoteapp.feature_note.presentation.note_list.NoteListState
import com.example.mongodbnoteapp.feature_note.redux.BaseContainer
import kotlinx.coroutines.CoroutineScope

class NoteListContainer(
    noteUseCases: NoteUseCases,
    scope: CoroutineScope
): BaseContainer<NoteListAction, NoteListState, NoteListSideEffect> (
    initialState = NoteListState(),
    scope = scope,
    reducer = NoteListReducer(),
    middleware = listOf(
        NoteListEffectMiddleware(),
        NoteLocalDataMiddleware(noteUseCases)
    ),
)