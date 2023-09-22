package com.example.mongodbnoteapp.feature_note.di

import android.app.Application
import com.example.mongodbnoteapp.feature_note.data.NoteDto
import com.example.mongodbnoteapp.feature_note.data.data_source.MongoDaoImpl
import com.example.mongodbnoteapp.feature_note.data.data_source.NoteDao
import com.example.mongodbnoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.mongodbnoteapp.feature_note.domain.repository.NoteRepository
import com.example.mongodbnoteapp.feature_note.domain.use_case.AddNote
import com.example.mongodbnoteapp.feature_note.domain.use_case.DeleteNote
import com.example.mongodbnoteapp.feature_note.domain.use_case.GetAllNotes
import com.example.mongodbnoteapp.feature_note.domain.use_case.GetNoteById
import com.example.mongodbnoteapp.feature_note.domain.use_case.NoteUseCases
import com.example.mongodbnoteapp.feature_note.domain.use_case.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                NoteDto::class
            )
        )
            .compactOnLaunch()
            .build()
        return Realm.open(config)
    }

    @Provides
    @Singleton
    fun provideNoteDao(realm: Realm): NoteDao {
        return MongoDaoImpl(realm)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getAllNotes = GetAllNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNoteById = GetNoteById(repository),
            updateNote = UpdateNote(repository)
        )
    }
}