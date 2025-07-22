package com.example.e_commerce.Domain.UseCase

import com.example.e_commerce.Data.Repository.OrderedProductImpl
import com.example.e_commerce.Domain.Entity.Product

class OrderUseCase {
    private val orderedProductRepo=OrderedProductImpl()
    suspend fun addOrderedProduct(userId:Int,productId:Int){
        orderedProductRepo.addToOrderedProduct(productId,userId)
    }
    suspend fun getOrderedProducts(userId:Int):List<Product>{
       return orderedProductRepo.getOrderedProduct(userId)
    }
}