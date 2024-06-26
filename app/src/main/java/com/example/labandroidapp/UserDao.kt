package com.example.labandroidapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert // insert user to DB
    fun insert(user: User)

    // query to get all user data from DB
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    fun getUser(email: String, password: String): User?
}
