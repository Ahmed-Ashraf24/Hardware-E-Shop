package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("Select * from  User where email ==:email and hashPassword ==:password")
    fun getUser(email :String , password :String): UserEntity?
    @Query("Select * from  User where email ==:email ")
    fun getUserByEmail(email :String): UserEntity?
    @Insert
    fun addUser(user: UserEntity)

}