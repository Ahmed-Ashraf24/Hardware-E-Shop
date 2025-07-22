package com.example.e_commerce.Domain.UseCase

import com.example.e_commerce.Data.Repository.LocalLoginRepo
import com.example.e_commerce.Domain.Entity.User

class LoginUseCase {
    suspend fun login(email:String, password:String): User?{
        return LocalLoginRepo().login(email,password)
    }
}