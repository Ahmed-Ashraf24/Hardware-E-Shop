package com.example.e_commerce.Domain.UseCase

import com.example.e_commerce.Data.Repository.ProductRepo
import com.example.e_commerce.Domain.Entity.Product

class ProductUseCase {
    suspend fun getAllProducts():List<Product>{
        return ProductRepo().getAllProducts()
    }
}