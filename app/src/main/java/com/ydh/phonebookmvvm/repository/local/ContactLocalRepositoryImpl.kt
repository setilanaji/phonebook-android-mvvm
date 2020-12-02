package com.ydh.phonebookmvvm.repository.local

import com.ydh.phonebookmvvm.repository.ContactLocalRepository
import com.ydh.phonebookmvvm.repository.local.dao.ContactDao
import com.ydh.phonebookmvvm.repository.local.entity.ContactEntity

class ContactLocalRepositoryImpl(private val dao: ContactDao): ContactLocalRepository{
    override suspend fun getAllContact(): List<ContactEntity> {
        return dao.getAllContact()
    }

    override suspend fun insertContact(contactEntity: ContactEntity) {
       return dao.insertContact(contactEntity)
    }

    override suspend fun deleteContact(contactEntity: ContactEntity) {
        return dao.deleteContact(contactEntity)
    }

    override suspend fun isFavorite(id: Long): Boolean {
        return dao.isFavorite(id)
    }

}