package com.example.mongodbnoteapp.feature_note.domain.auth_redux

import android.content.IntentSender
import com.example.mongodbnoteapp.feature_note.redux.SideEffect

sealed interface AuthSideEffect: SideEffect{
    data class SignInIntentSender(val intentSender: IntentSender): AuthSideEffect
    data class SignInFail(val message: String): AuthSideEffect
    data class SignUpIntentSender(val intentSender: IntentSender): AuthSideEffect
    data class SignUpFail(val message: String): AuthSideEffect
    data class MongoAuthFail(val message: String): AuthSideEffect

    object MongoAuthSuccess: AuthSideEffect

}