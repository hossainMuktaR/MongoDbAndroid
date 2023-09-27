package com.example.mongodbnoteapp.feature_note.domain.auth_redux

import com.example.mongodbnoteapp.feature_note.redux.Action
import com.google.android.gms.auth.api.identity.SignInClient

sealed class AuthAction: Action {
    data class SignIn(val oneTapClient: SignInClient) : AuthAction()
    data class SignUp(val oneTapClient: SignInClient): AuthAction()
    data class SignInWithMongoAtlas(val tokenId: String): AuthAction()
}