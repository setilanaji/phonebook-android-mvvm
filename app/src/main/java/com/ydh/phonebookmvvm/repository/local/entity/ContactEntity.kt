package com.ydh.phonebookmvvm.repository.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ydh.phonebookmvvm.model.ContactModel

@Entity(tableName = "contact_table")
data class ContactEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "phone")
    var phone: String = "",
    @ColumnInfo(name = "job")
    var job: String = "",
    @ColumnInfo(name = "company")
    var company: String = "",
    @ColumnInfo(name = "email")
    var email : String = "",
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "image")
    var image: String?,
    @ColumnInfo(name = "imageName")
    var imageName: String?,
)

fun ContactEntity.toModel() = ContactModel(id, name, phone, job, company, email, userId, image, imageName)