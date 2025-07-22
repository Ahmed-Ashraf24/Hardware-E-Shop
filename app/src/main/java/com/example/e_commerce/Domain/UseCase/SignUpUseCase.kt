package com.example.e_commerce.Domain.UseCase

import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Data.Repository.RegisterRepo

class SignUpUseCase {
    suspend fun signUp(user: UserEntity){
               RegisterRepo().register(user)
    }
}