package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name:String,
    val email:String,
    val gender:String,
    val hashPassword:String,
    val passwordSalt :String,
    val isVerified: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val profileImage :String,
    val phoneNumber: String? = null,
    val address: String? = null
    )
