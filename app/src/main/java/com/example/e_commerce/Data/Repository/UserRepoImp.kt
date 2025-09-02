package com.example.e_commerce.Data.Repository

import com.example.e_commerce.Data.DataSource.DatabaseClient
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Domain.RepoInterface.UserRepo

class UserRepoImp : UserRepo {
    private val localDatabase:DatabaseClient=LocalDatabase()
    override suspend fun editUserInfo(
        userId: Int,
        name: String,
        email: String,
        address: String?,
        phoneNumber: String?
    ) {
        localDatabase.editUserInfo(userId,name,email,address,phoneNumber)

    }

    override suspend fun changeUserPassword(
        userId: Int,
        userHashPassword: String,
        userPasswordSalt: String
    ) {
        localDatabase.changeUserPassword(userId,userHashPassword,userPasswordSalt)
    }
}