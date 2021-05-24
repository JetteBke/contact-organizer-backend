package com.example.cmsbackend

import java.io.BufferedInputStream
import java.io.InputStream
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

private val ourLogger = KotlinLogging.logger {}

@Service
class ContactService(
    val dbClient: ContactDbClient
) {
    fun getContacts(): List<Any> {
        ourLogger.info { "Getting contacts" }
        return dbClient.getContacts()
    }

    fun saveContact(contactData: Map<String, String>) {
        ourLogger.info { "The following data will be converted to contact: $contactData" }
        dbClient.saveContact(contactData)
    }

    fun updateContact(contactData: Map<String, String>) {
        ourLogger.info { "The following data will be converted to contact: $contactData" }
        val contact = contactData.toContact()
        dbClient.updateContact(contact)
    }

    fun getContact(contactId: Int): Any {
        ourLogger.info { "Getting contact with id: $contactId" }
        return dbClient.getContact(contactId)
    }

    fun uploadFile(file: MultipartFile) {
        ourLogger.info { "Uploading file ${file.originalFilename}" }
        val inputStream: InputStream = BufferedInputStream(file.inputStream)
        val contactData = loadDataFromCsv(inputStream)
        dbClient.insertContactsFromFile(contactData)
        ourLogger.info { "Inserted ${contactData.size} contacts" }
    }
}
