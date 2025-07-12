package com.aditya.springboot_kotlin.database.controller

import com.aditya.springboot_kotlin.database.model.NotesModel
import com.aditya.springboot_kotlin.database.repository.DatabaseRepository
import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.hibernate.validator.constraints.NotBlank
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/notes")
class NotesController(private val reposjitory: DatabaseRepository) {

    data class NoteRequest(
        val id: String?,
        @field:NotBlank(message = "Title can't be blank.")
        val title: String,
        val content: String,
        val color: Long,
    )

    data class NoteResponse(
        val id: String,
        val title: String,
        val content: String,
        val color: Long,
        val createdAt: Instant
    )

    @PostMapping
    fun save(
        @Valid @RequestBody body: NoteRequest
    ): NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        val note= reposjitory.save(
            NotesModel(
                notesid = body.id?.let { ObjectId(it)} ?: ObjectId.get(),
                title = body.title,
                content = body.content,
                color = body.color,
                time= Instant.now(),
                ownerId = ObjectId()
            )
        )

        return note.toResponse()

    }

    @GetMapping
    fun findByOwnerID(
        @RequestParam(required = true) ownerId: String
    ): List<NoteResponse>{


        return reposjitory.findByOwnerId(ObjectId(ownerId)).map {
            it.toResponse()

        }




    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteById(@PathVariable id: String) {
        val note = reposjitory.findById(ObjectId(id)).orElseThrow {
            IllegalArgumentException("Note not found")
        }
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        if(note.ownerId.toHexString() == ownerId) {
            reposjitory.deleteById(ObjectId(id))
        }
    }

    private fun NotesModel.toResponse(): NotesController.NoteResponse{

         return NoteResponse(
            id = notesid.toHexString(),
            title = title,
            content = content,
            color = color,
            createdAt = time

        )

    }


    @PutMapping(path = ["/{id}"])
    fun updateNote(
        @PathVariable id: String,
        @Valid @RequestBody body: NoteRequest
    ): NoteResponse {
        val ownerId = SecurityContextHolder.getContext().authentication.principal as String
        val existingNote = reposjitory.findById(ObjectId(id)).orElseThrow {
            IllegalArgumentException("Note not found")
        }

        if (existingNote.ownerId.toHexString() != ownerId) {
            throw IllegalAccessException("You do not have permission to update this note.")
        }

        val updatedNote = existingNote.copy(
            title = body.title,
            content = body.content,
            color = body.color,
            time = Instant.now()  // Optional: update timestamp
        )

        val savedNote = reposjitory.save(updatedNote)

        return savedNote.toResponse()
    }

}