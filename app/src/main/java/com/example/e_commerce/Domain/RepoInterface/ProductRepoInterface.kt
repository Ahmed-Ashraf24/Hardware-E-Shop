package com.example.e_commerce.Domain.RepoInterface

import com.example.e_commerce.Domain.Entity.Product

interface ProductRepoInterface {
    suspend fun getAllProducts():List<Product>
}