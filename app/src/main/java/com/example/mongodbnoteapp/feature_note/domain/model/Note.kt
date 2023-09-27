package com.example.mongodbnoteapp.feature_note.domain.model

import com.example.mongodbnoteapp.feature_note.data.NoteDto
import com.example.mongodbnoteapp.ui.theme.BabyBlue
import com.example.mongodbnoteapp.ui.theme.LightGreen
import com.example.mongodbnoteapp.ui.theme.RedOrange
import com.example.mongodbnoteapp.ui.theme.RedPink
import com.example.mongodbnoteapp.ui.theme.Violet
import io.realm.kotlin.types.RealmInstant
import org.mongodb.kbson.ObjectId
import kotlin.random.Random

data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    val userId: String,
    val id: Int? = null
){
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink )
    }
    fun toNoteDto(): NoteDto {
        return NoteDto(
            noteId = id ?: Random.nextInt(),
            title = title,
            content = content,
            color = color,
            userId = userId
        )
    }
}

class InvalidNoteException( message: String): Exception(message)
