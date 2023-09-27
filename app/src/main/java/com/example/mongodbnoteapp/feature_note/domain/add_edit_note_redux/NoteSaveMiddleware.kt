package com.example.mongodbnoteapp.feature_note.domain.add_edit_note_redux

import androidx.compose.ui.unit.Constraints
import com.example.mongodbnoteapp.feature_note.Utils.Constants
import com.example.mongodbnoteapp.feature_note.Utils.Constants.APP_ID
import com.example.mongodbnoteapp.feature_note.domain.model.InvalidNoteException
import com.example.mongodbnoteapp.feature_note.domain.model.Note
import com.example.mongodbnoteapp.feature_note.domain.use_case.NoteUseCases
import com.example.mongodbnoteapp.feature_note.presentation.add_edit_note.AddEditNoteState
import com.example.mongodbnoteapp.feature_note.redux.Container
import com.example.mongodbnoteapp.feature_note.redux.Middleware
import io.realm.kotlin.mongodb.App

class NoteSaveMiddleware(
    private val noteUseCases: NoteUseCases
): Middleware<AddEditNoteAction, AddEditNoteState, AddEditNoteSideEffect> {
    override suspend fun process(
        action: AddEditNoteAction,
        currentState: AddEditNoteState,
        container: Container<AddEditNoteAction, AddEditNoteState, AddEditNoteSideEffect>
    ) {
        val user = App.create(Constants.APP_ID).currentUser
        when(action) {
            is AddEditNoteAction.SaveNote -> {
                val note = Note(
                    title = currentState.noteTitle,
                    content = currentState.noteContent,
                    color = currentState.noteColor,
                    timeStamp = System.currentTimeMillis(),
                    id = currentState.currentNoteid,
                    userId = App.create(APP_ID).currentUser!!.id
                )
                try {
                    if(action.updateOrNot) {
                        noteUseCases.updateNote(note)
                    }else{
                        noteUseCases.addNote(note)
                    }

                    container.emitSideEffect(AddEditNoteSideEffect.SaveNote)
                } catch (e: InvalidNoteException){
                    container.emitSideEffect(AddEditNoteSideEffect.ShowSnackbar(
                        message = e.message ?: "Couldn't Save Note"
                    ))
                }
            }
            is AddEditNoteAction.FetchNoteById -> {
                noteUseCases.getNoteById(action.noteId)?.also { note ->
                    container.dispatch(AddEditNoteAction.FetchNoteComplated(note))
                }
            }
            else -> { }
        }
    }

}