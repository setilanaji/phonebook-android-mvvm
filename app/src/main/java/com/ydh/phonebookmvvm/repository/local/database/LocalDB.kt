package com.ydh.phonebookmvvm.repository.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ydh.phonebookmvvm.repository.local.dao.ContactDao
import com.ydh.phonebookmvvm.repository.local.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class LocalDB: RoomDatabase() {
    abstract fun dao(): ContactDao

    companion object{
        private lateinit var localDB: LocalDB
        private const val DATABASE_NAME = "contact.local.databases.db"

        fun getDB(context: Context): LocalDB {
            if (!this::localDB.isInitialized){
                localDB = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    DATABASE_NAME
                ).build()
            }
            return localDB
        }
    }

}