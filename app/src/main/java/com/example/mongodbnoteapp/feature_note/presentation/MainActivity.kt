package com.example.mongodbnoteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mongodbnoteapp.feature_note.Utils.Constants.APP_ID
import com.example.mongodbnoteapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.mongodbnoteapp.feature_note.presentation.auth.AuthScreen
import com.example.mongodbnoteapp.feature_note.presentation.auth.GoogleAuthClient
import com.example.mongodbnoteapp.feature_note.presentation.note_list.NoteListScreen
import com.example.mongodbnoteapp.feature_note.presentation.util.Screen
import com.example.mongodbnoteapp.ui.theme.NoteAppCleanArchMviMvvMTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppCleanArchMviMvvMTheme {
                Surface(color = MaterialTheme.colorScheme.background)
                {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = getStartDestination() )
                    {
                        composable(
                            route = Screen.AuthScreen.route
                        ){
                            AuthScreen(navController)
                        }
                        composable(
                            route = Screen.NoteListScreen.route
                        ){
                            NoteListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            val noteColor = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(navController = navController, noteColor = noteColor)
                        }
                    }
                }
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.NoteListScreen.route
    else Screen.AuthScreen.route
}