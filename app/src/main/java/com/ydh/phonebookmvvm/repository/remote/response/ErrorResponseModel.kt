package com.ydh.phonebookmvvm.repository.remote.response

import com.google.gson.annotations.SerializedName


data class ErrorResponseModel (
        @SerializedName("data")
        val data: String,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("message")
        val message: String,
)