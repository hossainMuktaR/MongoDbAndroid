package com.example.mongodbnoteapp.feature_note.domain.auth_redux

import com.example.mongodbnoteapp.feature_note.domain.add_edit_note_redux.AddEditNoteAction
import com.example.mongodbnoteapp.feature_note.presentation.auth.AuthState
import com.example.mongodbnoteapp.feature_note.presentation.auth.GoogleAuthClient
import com.example.mongodbnoteapp.feature_note.redux.BaseContainer
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope

class AuthContainer(
    googleAuthClient: GoogleAuthClient,
    scope: CoroutineScope
) : BaseContainer<AuthAction, AuthState, AuthSideEffect> (
    initialState = AuthState(),
    scope = scope,
    reducer = AuthReducer(),
    middleware = listOf(
        SigningMiddleware(googleAuthClient)
    ),
)