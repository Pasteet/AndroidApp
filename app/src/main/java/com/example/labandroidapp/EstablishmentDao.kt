package com.example.labandroidapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EstablishmentDao {
    @Insert // insert establishment to DB
    fun insert(establishment: Establishment)


    // query to get all establishment data from DB
    @Query("SELECT * FROM establishments")
    fun getAll(): List<Establishment>
}
