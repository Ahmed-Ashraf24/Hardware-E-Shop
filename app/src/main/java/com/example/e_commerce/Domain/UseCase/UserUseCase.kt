package com.example.e_commerce.Domain.UseCase

import com.example.e_commerce.Domain.RepoInterface.UserRepo

class UserUseCase (private val userRepo: UserRepo){
    suspend fun editUserInfo(
        userId: Int, name: String, email: String, address: String?, phoneNumber: String?
    ){
        userRepo.editUserInfo(userId,name,email,address,phoneNumber)
    }
    suspend fun changeUserPassword(userId: Int, userHashPassword: String, userPasswordSalt: String){
        userRepo.changeUserPassword(userId,userHashPassword,userPasswordSalt)
    }
}