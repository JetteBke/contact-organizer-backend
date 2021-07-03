package com.example.cmsbackend

import com.example.cmsbackend.db.NoteTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component
class NoteDbClient {
    fun saveNote(note: Note) {
        transaction {
            NoteTable.insert {
                it[createdAt] = note.createdAt
                it[updatedAt] = note.updatedAt
                it[text] = note.text
                it[contactId] = note.contactId
            }
        }
    }

    fun getNotes(contactId: Int): List<Note> {
        return transaction {
            NoteTable.select { NoteTable.contactId eq contactId }.map {
                Note(
                    contactId = it[NoteTable.contactId],
                    text = it[NoteTable.text].toString(),
                    createdAt = it[NoteTable.createdAt].toLong(),
                    updatedAt = it[NoteTable.updatedAt]?.toLong(),
                )
            }
        }
    }
}