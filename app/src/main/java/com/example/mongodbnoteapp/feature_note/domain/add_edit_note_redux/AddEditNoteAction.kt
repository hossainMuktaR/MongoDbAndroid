package com.example.mongodbnoteapp.feature_note.domain.add_edit_note_redux

import androidx.compose.ui.focus.FocusState
import com.example.mongodbnoteapp.feature_note.domain.model.Note
import com.example.mongodbnoteapp.feature_note.redux.Action

sealed class AddEditNoteAction: Action{
    data class TitleChanged(val value: String): AddEditNoteAction()
    data class TitleFocusChanged(val focusState: FocusState, val titleValue: String): AddEditNoteAction()
    data class ContentChanged(val value: String): AddEditNoteAction()
    data class ContentFocusChanged(val focusState: FocusState,val contentValue: String ): AddEditNoteAction()
    data class ColorChanged(val color: Int): AddEditNoteAction()
    data class SaveNote(val updateOrNot: Boolean): AddEditNoteAction()
    data class FetchNoteById(val noteId: Int): AddEditNoteAction()
    data class FetchNoteComplated(val note: Note): AddEditNoteAction()
}
