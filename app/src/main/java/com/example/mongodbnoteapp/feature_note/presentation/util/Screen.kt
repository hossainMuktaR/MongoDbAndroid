package com.example.mongodbnoteapp.feature_note.presentation.util

sealed class Screen(val route: String){
    object NoteListScreen: Screen("note_list_Screen")
    object AddEditNoteScreen: Screen("add_edit_note_Screen")
    object AuthScreen: Screen("auth_Screen")
}
