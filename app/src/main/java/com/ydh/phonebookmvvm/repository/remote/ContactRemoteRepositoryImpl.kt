package com.ydh.phonebookmvvm.repository.remote

import com.ydh.phonebookmvvm.repository.remote.request.BodyAddContact
import com.ydh.phonebookmvvm.model.ContactModel
import com.ydh.phonebookmvvm.repository.remote.response.ResponseModel
import com.ydh.phonebookmvvm.repository.ContactRemoteRepository
import com.ydh.phonebookmvvm.repository.remote.service.ContactService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call

class ContactRemoteRepositoryImpl (private val service: ContactService): ContactRemoteRepository{


    override suspend fun getAllContact(token: String ): ResponseModel<List<ContactModel>>{
        return service.getAllContact(token)
    }

    override suspend fun deleteContact(token: String, id: Int): ResponseModel<String>{
        return  service.deleteContact(token, id)
    }

    override suspend fun addContact(token: String, bodyAddContact: BodyAddContact): ResponseModel<ContactModel>{
        return service.addContact(
            token,
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.name),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.phone),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.email),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.job),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.company)
        )
    }

    override suspend fun updateContact(token: String, id: Int, bodyAddContact: BodyAddContact): ResponseModel<ContactModel>{
        return service.updateContact(
            token,
            id,
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.name),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.phone),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.email),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.job),
            RequestBody.create(MediaType.parse("text/plain"), bodyAddContact.company)
        )
    }

}