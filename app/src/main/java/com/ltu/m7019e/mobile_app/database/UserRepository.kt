package com.ltu.m7019e.mobile_app.database

import com.ltu.m7019e.mobile_app.model.User

class UserRepository(private val userDao: UserDao) {


    suspend fun login(username: String, password: String): Boolean {
        val user = userDao.getUser(username, password)
        return user != null
    }

    suspend fun register(user: User) {
        userDao.insertUser(user)
    }
}
