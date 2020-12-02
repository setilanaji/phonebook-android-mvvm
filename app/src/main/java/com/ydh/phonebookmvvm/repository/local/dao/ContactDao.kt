package com.ydh.phonebookmvvm.repository.local.dao

import androidx.room.*
import com.ydh.phonebookmvvm.repository.local.entity.ContactEntity

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact_table ORDER BY ID ASC")
    suspend fun getAllContact() : List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(contactEntity: ContactEntity)

    @Update
    suspend fun updateContact(contactEntity: ContactEntity)

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)

    @Query("SELECT EXISTS(SELECT * FROM contact_table WHERE id = :id)")
    suspend fun isFavorite(id: Long): Boolean
}