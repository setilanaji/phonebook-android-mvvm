package com.ydh.phonebookmvvm.repository.remote.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ydh.phonebookmvvm.repository.remote.service.ContactService
import com.ydh.phonebookmvvm.repository.remote.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val BASE_URL = "https://phone-book-api.herokuapp.com/"


    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val interceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val userService: UserService by lazy { retrofit.create(UserService::class.java) }

    val contactService: ContactService by lazy { retrofit.create(ContactService::class.java) }

}