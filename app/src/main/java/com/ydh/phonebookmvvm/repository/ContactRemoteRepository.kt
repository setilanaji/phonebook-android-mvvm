package com.ydh.phonebookmvvm.repository

import com.ydh.phonebookmvvm.repository.remote.request.BodyAddContact
import com.ydh.phonebookmvvm.model.ContactModel
import com.ydh.phonebookmvvm.repository.remote.response.ResponseModel
import retrofit2.Call

interface ContactRemoteRepository {

    suspend fun getAllContact(token: String ): ResponseModel<List<ContactModel>>
    suspend fun deleteContact(token: String, id: Int): ResponseModel<String>
    suspend fun addContact(token: String, bodyAddContact: BodyAddContact): ResponseModel<ContactModel>
    suspend fun updateContact(token: String, id: Int, bodyAddContact: BodyAddContact): ResponseModel<ContactModel>

}