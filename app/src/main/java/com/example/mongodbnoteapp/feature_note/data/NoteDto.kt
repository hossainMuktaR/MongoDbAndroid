package com.example.mongodbnoteapp.feature_note.data

import com.example.mongodbnoteapp.feature_note.domain.model.Note
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import kotlin.random.Random

class NoteDto(): RealmObject {
    @PrimaryKey
    var noteId: Int = Random.nextInt()
    var title: String = ""
    var content: String = ""
    var timeStamp: RealmInstant = RealmInstant.now()
    var color: Int = -1

    constructor(
        noteId: Int,
        title: String,
        content: String,
        color: Int
    ) : this() {
        this.noteId= noteId
        this.title = title
        this.content = content
        this.color = color
    }
    fun toNote(): Note{
        return Note(
            id = noteId,
            title = title,
            content = content,
            timeStamp = timeStamp.epochSeconds,
            color = color
        )
    }
}

//class Item() : RealmObject {
//    @PrimaryKey
//    var _id: ObjectId = ObjectId()
//    var isComplete: Boolean = false
//    var summary: String = ""
//    var owner_id: String = ""
//    constructor(ownerId: String = "") : this() {
//        owner_id = ownerId
//    }
//}
//data class Note(
//    val title: String,
//    val content: String,
//    val timeStamp: Long,
//    val color: Int,
//    val id: Int? = null
//)