package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "productId"])
data class UserProductRef(
    val userId:Int,
    val productId:Int

)
