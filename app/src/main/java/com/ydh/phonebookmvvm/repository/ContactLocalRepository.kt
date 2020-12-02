package com.ydh.phonebookmvvm.repository

import com.ydh.phonebookmvvm.repository.local.entity.ContactEntity

interface ContactLocalRepository {
    suspend fun getAllContact(): List<ContactEntity>
    suspend fun insertContact(contactEntity: ContactEntity)
    suspend fun deleteContact(contactEntity: ContactEntity)
    suspend fun isFavorite(id: Long): Boolean
}