package com.example.e_commerce.Data.Repository

import com.example.e_commerce.Data.DataSource.DatabaseClient
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Data.Mapper.UserMapper
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.RepoInterface.LoginRepoInterface

class LocalLoginRepo : LoginRepoInterface {
    private val database: DatabaseClient = LocalDatabase()
    override suspend fun login(email: String, password: String): User? {
        val user = database.getUserFromEmailAndPassword(email, password)
        return user?.let { UserMapper.toUser(it) }
    }
}