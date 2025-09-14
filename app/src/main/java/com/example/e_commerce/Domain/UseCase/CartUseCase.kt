package com.example.e_commerce.Domain.UseCase

import com.example.e_commerce.Data.Repository.CartRepoImpl
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.RepoInterface.CartRepo

class CartUseCase {
    val cart:CartRepo=CartRepoImpl()
    suspend fun addToCart(userId:Int,productId:Int,quantity:Int){
        cart.addProductToCart(productId,userId,quantity)
    }
    suspend fun getProducts(user: User):List<Product>{
        return cart.getCartProducts(user)
    }
    suspend fun removeItemFromCart(userId: Int,productId: Int){
        cart.removeItemFromCart(productId=productId,userId=userId)
    }
}