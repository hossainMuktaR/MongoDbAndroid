package com.example.mongodbnoteapp.feature_note.domain.auth_redux

import com.example.mongodbnoteapp.feature_note.Utils.Constants.APP_ID
import com.example.mongodbnoteapp.feature_note.presentation.auth.AuthState
import com.example.mongodbnoteapp.feature_note.presentation.auth.GoogleAuthClient
import com.example.mongodbnoteapp.feature_note.redux.Container
import com.example.mongodbnoteapp.feature_note.redux.Middleware
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.GoogleAuthType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SigningMiddleware(
    val googleAuthClient: GoogleAuthClient
) : Middleware<AuthAction, AuthState, AuthSideEffect> {
    override suspend fun process(
        action: AuthAction,
        currentState: AuthState,
        container: Container<AuthAction, AuthState, AuthSideEffect>
    ) {
        when (action) {
            is AuthAction.SignIn -> {
                action.oneTapClient.beginSignIn(googleAuthClient.buildSignInRequest())
                    .addOnSuccessListener { result ->
                        container.scope.launch {
                            container.emitSideEffect(
                                AuthSideEffect.SignInIntentSender(
                                    result.pendingIntent.intentSender
                                )
                            )
                        }
                    }.addOnFailureListener {
                        container.scope.launch {
                            container.emitSideEffect(
                                AuthSideEffect.SignInFail(it.message ?: "unknown Error")
                            )
                        }
                    }
            }

            is AuthAction.SignUp -> {
                action.oneTapClient.beginSignIn(googleAuthClient.buildSignUpRequest())
                    .addOnSuccessListener { result ->
                        container.scope.launch {
                            container.emitSideEffect(
                                AuthSideEffect.SignUpIntentSender(
                                    result.pendingIntent.intentSender
                                )
                            )
                        }
                    }.addOnFailureListener {
                        container.scope.launch {
                            container.emitSideEffect(
                                AuthSideEffect.SignUpFail(it.message ?: "unknown Error")
                            )
                        }
                    }
            }

            is AuthAction.SignInWithMongoAtlas -> {
                try{
                    val result = App.create(APP_ID).login(
                        Credentials.google(token = action.tokenId, type = GoogleAuthType.ID_TOKEN)
                    ).loggedIn
                    if(result) {
                        delay(500)
                        container.emitSideEffect(AuthSideEffect.MongoAuthSuccess)
                    }else{
                        container.emitSideEffect(
                            AuthSideEffect.MongoAuthFail(" Mongo Auth Fail - result")
                        )
                    }
                }catch (e: Exception) {
                    container.emitSideEffect(
                        AuthSideEffect.MongoAuthFail(e.message ?: " Mongo Auth Fail")
                    )
                }
            }

            else -> {

            }
        }
    }
}