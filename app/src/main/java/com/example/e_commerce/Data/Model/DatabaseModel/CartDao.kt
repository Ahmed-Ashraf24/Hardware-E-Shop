package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateCartItem(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE userId = :userId AND productId = :productId")
    suspend fun removeCartItem(userId: Int, productId: Int)

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCart(userId: Int)

    @Query("""
        SELECT Product.* FROM Product
        INNER JOIN cart_items ON Product.id = cart_items.productId
        WHERE cart_items.userId = :userId
    """)
    suspend fun getCartProducts(userId: Int): List<ProductEntity>

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    suspend fun getCartItems(userId: Int): List<CartItemEntity>
}