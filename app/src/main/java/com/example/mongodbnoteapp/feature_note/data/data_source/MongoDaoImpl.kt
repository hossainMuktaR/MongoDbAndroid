package com.example.mongodbnoteapp.feature_note.data.data_source

import android.util.Log
import com.example.mongodbnoteapp.feature_note.data.NoteDto
import com.example.mongodbnoteapp.feature_note.domain.model.Note
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class MongoDaoImpl(
    private val realm: Realm
): NoteDao {
    override suspend fun getAllNotes(): List<Note> {
        return realm.query<NoteDto>().find().map { it.toNote() }
    }

    override suspend fun getNoteById(noteId: Int): Note {
        return realm.query<NoteDto>(query = "noteId == $0", noteId).find().first().toNote()
    }

    override suspend fun insertNote(noteDto: NoteDto) {
        realm.write {
            copyToRealm(noteDto)
        }
    }

    override suspend fun updateNote(noteDto: NoteDto) {
        realm.write {
            val note = query<NoteDto>(query = "noteId == $0", noteDto.noteId).first().find()
            note?.apply {
                timeStamp = noteDto.timeStamp
                title = noteDto.title
                content = noteDto.content
                color = noteDto.color
            }
        }
    }

    override suspend fun deleteNote(noteDto: NoteDto) {
        realm.write {
            val note = query<NoteDto>(query = "noteId == $0", noteDto.noteId).first().find()
            try {
                note?.let { delete(it) }
            } catch (e: Exception) {
                Log.d("MongoRepositoryImpl", "${e.message}")
            }
        }
    }
}