package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithProduct(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserProductRef::class,
            parentColumn = "userId",
            entityColumn = "productId"

        )
    )
    val products: List<ProductEntity>
)

