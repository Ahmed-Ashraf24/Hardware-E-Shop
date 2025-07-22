package com.example.e_commerce.Data.Mapper

import com.example.e_commerce.Data.Model.DatabaseModel.ProductEntity
import com.example.e_commerce.Domain.Entity.Product

class ProductMapper {
    companion object{
        fun toProduct(product: ProductEntity):Product{
            return Product(
                id = product.id
                ,name = product.name,
                category = product.category,
                price = product.price,
                imageURL = product.imageUrl,
                description = product.description,
                rating = "5")
        }
    }
}