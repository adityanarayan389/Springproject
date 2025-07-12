package com.aditya.springboot_kotlin.database.model


import org.bson.types.ObjectId


data class User(
    val email: String,
    val hashPassword: String,
    val id: ObjectId= ObjectId()
)