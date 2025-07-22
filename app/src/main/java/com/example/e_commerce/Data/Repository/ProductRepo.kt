package com.example.e_commerce.Data.Repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.e_commerce.Data.DataSource.DatabaseClient
import com.example.e_commerce.Data.DataSource.LocalDatabase
import com.example.e_commerce.Data.Mapper.ProductMapper
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.RepoInterface.ProductRepoInterface

class ProductRepo:ProductRepoInterface {
    val localDatabase:DatabaseClient=LocalDatabase()
    @SuppressLint("SuspiciousIndentation")
    override suspend fun getAllProducts(): List<Product> {
        val productList= mutableListOf<Product>()
        Log.d("size of database product result",localDatabase.getAllProducts().size.toString())
             localDatabase.getAllProducts().forEach {
                 Log.d("data returns from database",it.toString())
                 productList.add(ProductMapper.toProduct(it)) }
        return productList
    }
}