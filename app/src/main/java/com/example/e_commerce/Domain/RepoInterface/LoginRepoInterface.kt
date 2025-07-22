package com.example.e_commerce.Domain.RepoInterface

import com.example.e_commerce.Domain.Entity.User

interface LoginRepoInterface {
    suspend fun login(email :String ,password:String): User?
}