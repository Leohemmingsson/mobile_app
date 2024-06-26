package com.ltu.m7019e.mobile_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ltu.m7019e.mobile_app.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT country FROM users WHERE username = :username")
    suspend fun getUserCountryCode(username: String): String

    @Query("UPDATE users SET country = :countryCode, password = :password WHERE username = :username\n")
    suspend fun updateUser(countryCode: String, username: String, password: String)


}