package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserWithProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: UserProductRef)

    @Transaction
    @Query("SELECT * FROM User WHERE id = :userId")
    suspend fun getUserWithProducts(userId: Int): UserWithProduct

    @Transaction
    @Query("SELECT * FROM Product WHERE id = :productId")
    suspend fun getProductWithUsers(productId: Int): ProductWithUsers
}