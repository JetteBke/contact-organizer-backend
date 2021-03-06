package com.example.cmsbackend.contacts

import com.example.cmsbackend.db.ContactTable
import com.example.cmsbackend.db.NoteTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component

@Component
class ContactDbClient {
    fun getContacts(): List<Contact> {
        return transaction {
            ContactTable.selectAll().map {
                Contact(
                    id = it[ContactTable.id],
                    title = it[ContactTable.title],
                    firstName = it[ContactTable.firstName],
                    lastName = it[ContactTable.lastName],
                    company = it[ContactTable.company],
                    address = it[ContactTable.address],
                    postalCode = it[ContactTable.postalCode],
                    city = it[ContactTable.city],
                    phoneOne = it[ContactTable.phoneOne],
                    phoneTwo = it[ContactTable.phoneTwo],
                    emailOne = it[ContactTable.emailOne],
                    emailTwo = it[ContactTable.emailTwo],
                    oldNote = it[ContactTable.oldNote]
                )
            }
        }
    }

    fun getContact(contactId: Int): Contact {
        return transaction {
            ContactTable.select { ContactTable.id eq contactId }.map {
                Contact(
                    id = it[ContactTable.id],
                    title = it[ContactTable.title],
                    firstName = it[ContactTable.firstName],
                    lastName = it[ContactTable.lastName],
                    company = it[ContactTable.company],
                    address = it[ContactTable.address],
                    postalCode = it[ContactTable.postalCode],
                    city = it[ContactTable.city],
                    phoneOne = it[ContactTable.phoneOne],
                    phoneTwo = it[ContactTable.phoneTwo],
                    emailOne = it[ContactTable.emailOne],
                    emailTwo = it[ContactTable.emailTwo],
                    oldNote = it[ContactTable.oldNote]
                )
            }.first()
        }
    }

    fun saveContact(contact: ContactRequest) {
        insertContact(contact)
    }

    fun updateContact(contact: Contact) {
        transaction {
            ContactTable.update({ ContactTable.id eq contact.id }) {
                it[title] = contact.title
                it[firstName] = contact.firstName
                it[lastName] = contact.lastName
                it[company] = contact.company
                it[address] = contact.address
                it[postalCode] = contact.postalCode
                it[city] = contact.city
                it[phoneOne] = contact.phoneOne
                it[phoneTwo] = contact.phoneTwo
                it[emailOne] = contact.emailOne
                it[emailTwo] = contact.emailTwo
                it[oldNote] = contact.oldNote
            }
        }
    }

    fun deleteContact(contactId: Int) {
        transaction {
            NoteTable.deleteWhere { NoteTable.contactId eq contactId }
        }
        transaction {
            ContactTable.deleteWhere { ContactTable.id eq contactId }
        }
    }

    fun insertContactsFromFile(contactDataFromFile: List<ContactRequest>) {
        contactDataFromFile.map { insertContactFromFile(it) }
    }

    private fun insertContact(contact: ContactRequest) {
        transaction {
            ContactTable.insert {
                it[title] = contact.title
                it[firstName] = contact.firstName
                it[lastName] = contact.lastName
                it[company] = contact.company
                it[address] = contact.address
                it[postalCode] = contact.postalCode
                it[city] = contact.city
                it[phoneOne] = contact.phoneOne
                it[phoneTwo] = contact.phoneTwo
                it[emailOne] = contact.emailOne
                it[emailTwo] = contact.emailTwo
                it[oldNote] = contact.oldNote
            }
        }
    }

    private fun insertContactFromFile(contact: ContactRequest) {
        transaction {
            ContactTable.insert {
                it[title] = contact.title
                it[firstName] = contact.firstName
                it[lastName] = contact.lastName
                it[company] = contact.company
                it[address] = contact.address
                it[postalCode] = contact.postalCode
                it[city] = contact.city
                it[phoneOne] = contact.phoneOne
                it[phoneTwo] = contact.phoneTwo
                it[emailOne] = contact.emailOne
                it[emailTwo] = contact.emailTwo
                it[oldNote] = contact.oldNote
            }
        }
    }
}