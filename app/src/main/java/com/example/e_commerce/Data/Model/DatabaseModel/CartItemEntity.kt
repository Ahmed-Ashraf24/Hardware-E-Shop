package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "cart_items",
    primaryKeys = ["userId", "productId"],
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = ProductEntity::class, parentColumns = ["id"], childColumns = ["productId"])
    ]
)
data class CartItemEntity(
    val userId: Int,
    val productId: Int,
    val quantity: Int = 1
)
