package com.example.labandroidapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EstablishmentDao {
    @Insert
    fun insert(establishment: Establishment)

    @Query("SELECT * FROM establishments")
    fun getAll(): List<Establishment>
}
