package com.example.e_commerce.Domain.RepoInterface

import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity

interface RegisterRepoInterface {
    suspend fun register(user: UserEntity)
}