package com.example.labandroidapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class, Establishment::class], version = 3) // 2 entities in DB
@TypeConverters(MenuItemConverter::class) // convert data type for DB
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun establishmentDao(): EstablishmentDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // build DB instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration() // handle migration, will reset DB data!!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
