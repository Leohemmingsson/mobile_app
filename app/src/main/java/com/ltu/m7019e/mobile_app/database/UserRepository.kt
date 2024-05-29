package com.ltu.m7019e.mobile_app.database

import com.ltu.m7019e.mobile_app.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    suspend fun register(user: User) {
        userDao.insertUser(user)
    }
}
