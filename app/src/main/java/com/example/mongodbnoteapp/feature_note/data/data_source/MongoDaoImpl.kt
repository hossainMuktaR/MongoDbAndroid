package com.example.mongodbnoteapp.feature_note.data.data_source

import android.util.Log
import com.example.mongodbnoteapp.feature_note.Utils.Constants.APP_ID
import com.example.mongodbnoteapp.feature_note.data.NoteDto
import com.example.mongodbnoteapp.feature_note.domain.model.Note
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration

class MongoDaoImpl() : NoteDao {
    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureRealm()
    }

    private fun configureRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user,
                setOf(NoteDto::class)
            ).initialSubscriptions { sub ->
                add(
                    query = sub.query<NoteDto>(query = "userId == $0", user.id),
                    "userId Subscription"
                )
            }
                .waitForInitialRemoteData()
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

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