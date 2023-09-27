package com.example.mongodbnoteapp.feature_note.presentation.auth

import com.example.mongodbnoteapp.feature_note.redux.State

data class AuthState(
    val isLoading: Boolean = false
): State
