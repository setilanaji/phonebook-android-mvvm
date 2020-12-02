package com.ydh.phonebookmvvm.repository.remote.request

import com.google.gson.annotations.SerializedName

data class BodyAddContact (
    var name: String,
    var phone: String,
    var job: String,
    var company: String,
    var email : String,
    var image: String? = null
)