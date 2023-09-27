package com.example.mongodbnoteapp.feature_note.presentation.auth

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mongodbnoteapp.feature_note.domain.auth_redux.AuthSideEffect
import com.example.mongodbnoteapp.feature_note.presentation.util.Screen
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current as Activity
    val oneTapClient = Identity.getSignInClient(context)

    val state = viewModel.state.collectAsState().value
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    val credentials = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val tokenId = credentials.googleIdToken
                    if (tokenId != null) {
                        viewModel.signInWithMongoAtlas(tokenId)
                    }
                }
            } catch (e: ApiException) {
                Log.e(TAG, "${e.message}")
            }
        }
    )

    LaunchedEffect(true) {
        viewModel.sideEffect.collectLatest {
            when(it) {
                is AuthSideEffect.SignInFail -> {
                    viewModel.signUp(oneTapClient)
                    snackbarHostState.showSnackbar(
                        it.message, null, false, SnackbarDuration.Short
                    )
                }
                is AuthSideEffect.SignInIntentSender -> {
                    activityLauncher.launch(
                        IntentSenderRequest.Builder(
                            it.intentSender
                        ).build()
                    )
                }

                is AuthSideEffect.SignUpFail -> {
                    snackbarHostState.showSnackbar(
                        it.message, null, false, SnackbarDuration.Short
                    )
                    delay(2000)
                    navController.navigateUp()
                }
                is AuthSideEffect.SignUpIntentSender -> {
                    activityLauncher.launch(
                        IntentSenderRequest.Builder(
                            it.intentSender
                        ).build()
                    )
                }

                is AuthSideEffect.MongoAuthFail -> {
                    snackbarHostState.showSnackbar(
                        it.message, null, false, SnackbarDuration.Short
                    )
                    delay(2000)
                    navController.navigateUp()
                }
                AuthSideEffect.MongoAuthSuccess -> {
                    navController.popBackStack()
                    navController.navigate(Screen.NoteListScreen.route)
                }
            }
        }
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Button(
                enabled = !state.isLoading,
                onClick = {
                    viewModel.signIn(oneTapClient)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "SignIn Started",
                            duration = SnackbarDuration.Short,
                            withDismissAction = false
                        )
                    }
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Icon(imageVector = Icons.Default.Login, contentDescription = "login")
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Login with Google",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

        }
    }
}