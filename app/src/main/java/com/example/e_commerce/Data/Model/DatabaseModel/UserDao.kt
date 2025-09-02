package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("Select * from  User where email ==:email and hashPassword ==:password")
    fun getUser(email :String , password :String): UserEntity?
    @Query("Select * from  User where email ==:email ")
    fun getUserByEmail(email :String): UserEntity?
    @Insert
    fun addUser(user: UserEntity)

    @Query("""
        UPDATE User
        SET name = :name, 
            email = :email, 
            address = :address, 
            phoneNumber = :phoneNumber
        WHERE id = :userId
    """)
    suspend fun updateUserInfo(
        userId: Int,
        name: String,
        email: String,
        address: String?,
        phoneNumber: String?
    ): Int
    @Query("UPDATE User SET hashPassword=:hashPassword ,passwordSalt=:passwordSalt where id=:userId")
    suspend fun updateUserPassword(userId:Int ,hashPassword:String,passwordSalt:String)
}