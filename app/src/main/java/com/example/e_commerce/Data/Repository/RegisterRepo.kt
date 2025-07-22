package com.example.e_commerce.Data.Repository

import com.example.e_commerce.Data.DataSource.DatabaseClient
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Domain.RepoInterface.RegisterRepoInterface

class RegisterRepo:RegisterRepoInterface {
    private val database: DatabaseClient = LocalDatabase()

    override suspend fun register(user: UserEntity) {
        database.addUser(user)

    }
}