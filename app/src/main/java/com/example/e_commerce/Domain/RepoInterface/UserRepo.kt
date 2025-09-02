package com.example.e_commerce.Domain.RepoInterface

interface UserRepo {
    suspend fun editUserInfo(
        userId: Int, name: String, email: String, address: String?, phoneNumber: String?
    )
    suspend fun changeUserPassword(userId: Int, userHashPassword: String, userPasswordSalt: String)

}