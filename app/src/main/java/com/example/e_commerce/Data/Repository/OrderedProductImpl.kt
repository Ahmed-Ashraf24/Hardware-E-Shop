package com.example.e_commerce.Data.Repository

import com.example.e_commerce.Data.DataSource.DatabaseClient
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.RepoInterface.OrderedProductRepoInterface

class OrderedProductImpl:OrderedProductRepoInterface {
    private val localDatabase:DatabaseClient=LocalDatabase()
    override suspend fun addToOrderedProduct(productId: Int,userId:Int) {
    localDatabase.addOrderedProduct(productId,userId)
    }

    override suspend fun getOrderedProduct(userId: Int): List<Product> {
        return localDatabase.getOrderedProducts(userId)
    }
}