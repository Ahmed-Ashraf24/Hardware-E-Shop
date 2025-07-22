package com.example.e_commerce.Data.Repository

import com.example.e_commerce.Data.DataSource.DatabaseClient
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Data.Mapper.ProductMapper
import com.example.e_commerce.Data.Mapper.UserMapper
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.RepoInterface.CartRepo

class CartRepoImpl:CartRepo {
    val localDatabase: DatabaseClient = LocalDatabase()

    override suspend fun getCartProducts(user: User): List<Product> {
       val productList=mutableListOf<Product>()
        localDatabase.getCartProducts(user.id).forEach { productList.add(ProductMapper.toProduct(it)) }
        return productList
    }

    override suspend fun addProductToCart(productId:Int,userId: Int,quantaty:Int) {
        localDatabase.addToCart(productId = productId, userId = userId, quantity = quantaty)
    }
}