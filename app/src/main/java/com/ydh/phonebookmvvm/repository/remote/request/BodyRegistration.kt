package com.ydh.phonebookmvvm.repository.remote.request

import com.google.gson.annotations.SerializedName

data class BodyRegistration(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)