package com.example.mongodbnoteapp.feature_note.presentation.auth

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mongodbnoteapp.feature_note.Utils.Constants
import com.example.mongodbnoteapp.feature_note.Utils.Constants.APP_ID
import com.example.mongodbnoteapp.feature_note.domain.auth_redux.AuthAction
import com.example.mongodbnoteapp.feature_note.domain.auth_redux.AuthContainer
import com.example.mongodbnoteapp.feature_note.domain.auth_redux.AuthSideEffect
import com.example.mongodbnoteapp.feature_note.redux.Container
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    googleAuthClient: GoogleAuthClient
) : ViewModel() {

    private val container = AuthContainer(googleAuthClient,viewModelScope)
    val state: StateFlow<AuthState> = container.state
    val sideEffect: SharedFlow<AuthSideEffect> = container.sideEffect

    fun signIn(oneTapClient: SignInClient) = intent {
        dispatch(
            AuthAction.SignIn(oneTapClient)
        )
    }
    fun signUp(oneTapClient: SignInClient) = intent {
        dispatch(
            AuthAction.SignUp(oneTapClient)
        )
    }
    fun signInWithMongoAtlas( tokenId: String) = intent {
        dispatch(AuthAction.SignInWithMongoAtlas(tokenId))
    }
    private fun intent(transform: suspend Container<AuthAction, AuthState, AuthSideEffect>.()-> Unit ) {
        viewModelScope.launch {
            container.transform()
        }
    }

}
