package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductWithUsers(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserProductRef::class,
            parentColumn = "productId",
            entityColumn = "userId"
        )
    )
    val users: List<UserEntity>
)