package com.example.e_commerce.Domain.RepoInterface

import com.example.e_commerce.Domain.Entity.Product

interface OrderedProductRepoInterface {
    suspend fun addToOrderedProduct(productId: Int,userId:Int)
    suspend fun getOrderedProduct(userId:Int):List<Product>
}