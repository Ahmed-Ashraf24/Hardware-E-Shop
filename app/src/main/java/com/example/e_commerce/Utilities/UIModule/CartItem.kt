package com.example.e_commerce.Utilities.UIModule

import com.example.e_commerce.Domain.Entity.Product

data class CartItem(
    val product: Product,
    var quantity: Int
)