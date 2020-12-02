package com.ydh.phonebookmvvm.repository.remote.service

import com.ydh.phonebookmvvm.model.ContactModel
import com.ydh.phonebookmvvm.repository.remote.response.ResponseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ContactService {

    @GET("api/v1/contacts")
    suspend fun getAllContact(
        @Header("Authorization")
        token: String
    ): ResponseModel<List<ContactModel>>

    @DELETE("api/v1/contacts/{id}")
    suspend fun deleteContact(
            @Header("Authorization")
            token: String,
            @Path("id")
            id: Int
    ): ResponseModel<String>

    @Multipart
    @POST("api/v1/contacts")
    suspend fun addContact(
        @Header("Authorization")
        token: String,
        @Part("name")
        name: RequestBody,
        @Part("phone")
        phone: RequestBody,
        @Part("email")
        email: RequestBody,
        @Part("job")
        job: RequestBody,
        @Part("company")
        company: RequestBody,
        @Part("image")
        image: RequestBody? = null,
        ): ResponseModel<ContactModel>

    @Multipart
    @PUT("api/v1/contacts/{id}")
    suspend fun updateContact(
        @Header("Authorization")
        token: String,
        @Path("id")
        id: Int,
        @Part("name")
        name: RequestBody,
        @Part("phone")
        phone: RequestBody,
        @Part("email")
        email: RequestBody,
        @Part("job")
        job: RequestBody,
        @Part("company")
        company: RequestBody,
        @Part("image")
        image: RequestBody? = null,
    ): ResponseModel<ContactModel>
}