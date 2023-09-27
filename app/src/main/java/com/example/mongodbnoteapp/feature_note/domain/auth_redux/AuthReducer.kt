package com.example.mongodbnoteapp.feature_note.domain.auth_redux

import com.example.mongodbnoteapp.feature_note.presentation.auth.AuthState
import com.example.mongodbnoteapp.feature_note.redux.Reducer

class AuthReducer: Reducer<AuthAction, AuthState> {
    override fun reduce(action: AuthAction, currentState: AuthState): AuthState {
        return when(action) {
            is AuthAction.SignIn -> {
                currentState.copy(
                    isLoading = true
                )
            }
            is AuthAction.SignUp -> {
                currentState.copy(
                    isLoading = true
                )
            }
            else -> currentState
        }
    }

}