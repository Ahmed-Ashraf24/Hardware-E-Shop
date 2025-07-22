package com.example.e_commerce.Data.DataSource

import com.example.e_commerce.Data.Model.DatabaseModel.ProductEntity
import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Domain.Entity.Product

interface DatabaseClient {
 suspend fun getUserFromEmailAndPassword(email:String,password:String): UserEntity?
 suspend fun addUser(user: UserEntity)
 suspend fun getAllProducts():List<ProductEntity>
 suspend fun addProduct(product: ProductEntity)
 suspend fun addOrderedProduct(productId:Int,userId:Int)
 suspend fun addToCart(productId: Int, userId:Int, quantity:Int=1)
 suspend fun getCartProducts(userId:Int):List<ProductEntity>
 suspend fun getOrderedProducts(userId:Int):List<Product>
}