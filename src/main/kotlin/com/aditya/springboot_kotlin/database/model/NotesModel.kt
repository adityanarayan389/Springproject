package com.aditya.springboot_kotlin.database.model

import io.jsonwebtoken.lang.Objects
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("notes")
data class NotesModel(
    val title:String,
    val content: String,
    val color:Long,
    val time: Instant,
    val ownerId: ObjectId,
    @Id val notesid: ObjectId = ObjectId.get()
)
