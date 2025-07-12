package com.aditya.springboot_kotlin.database.repository

import com.aditya.springboot_kotlin.database.model.NotesModel
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface DatabaseRepository : MongoRepository<NotesModel, ObjectId> {
    fun findByOwnerId(ownerId: ObjectId): List<NotesModel>

}

