package com.example.e_commerce.Data.Model.DatabaseModel

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
    suspend fun addProduct(product: ProductEntity)

    @Query("Select * from Product ")
    suspend fun getAllProducts() : List<ProductEntity>

    @Query("Select * from Product where category==:category")
    suspend fun getProductsByCategory(category: String) : List<ProductEntity>

    @Query("select * from product where id==:id")
    suspend fun getProductById(id: String) : ProductEntity

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

}