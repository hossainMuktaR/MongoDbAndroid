package com.example.mongodbnoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mongodbnoteapp.ui.theme.BabyBlue
import com.example.mongodbnoteapp.ui.theme.LightGreen
import com.example.mongodbnoteapp.ui.theme.RedOrange
import com.example.mongodbnoteapp.ui.theme.RedPink
import com.example.mongodbnoteapp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink )
    }
}

class InvalidNoteException( message: String): Exception(message)
