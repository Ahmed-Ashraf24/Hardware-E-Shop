package com.example.e_commerce.Domain.RepoInterface

import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.Entity.User

interface CartRepo {
    suspend fun getCartProducts(user: User):List<Product>
    suspend fun addProductToCart(productId:Int,userId: Int,quantaty:Int=1)
    suspend fun removeItemFromCart(productId: Int,userId: Int)
}