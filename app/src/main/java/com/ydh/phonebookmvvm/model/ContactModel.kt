package com.ydh.phonebookmvvm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ydh.phonebookmvvm.repository.local.entity.ContactEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("job")
    var job: String = "",
    @SerializedName("company")
    var company: String = "",
    @SerializedName("email")
    var email : String = "",
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("image")
    var image: String?,
    @SerializedName("imageName")
    var imageName: String?,
): Parcelable

fun ContactModel.toEntity() = ContactEntity(id, name, phone, job, company, email, userId, image, imageName)