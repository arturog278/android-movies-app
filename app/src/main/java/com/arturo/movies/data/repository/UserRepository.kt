package com.arturo.movies.data.repository

import com.arturo.movies.data.local.dao.UserDao
import com.arturo.movies.model.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getUser(userId: Int): UserEntity? {
        return userDao.getUserById(userId)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

}