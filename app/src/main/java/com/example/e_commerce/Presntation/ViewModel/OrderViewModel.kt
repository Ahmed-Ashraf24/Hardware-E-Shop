package com.example.e_commerce.Presntation.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.UseCase.OrderUseCase
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val _orderedProductList = MutableLiveData<List<Product>>()
    val orderedProductList: LiveData<List<Product>> = _orderedProductList
    fun getOrderedProducts(user: User){
        viewModelScope.launch {
            Log.d("the product ViewModel is called","this is called")
            val productUseCase= OrderUseCase().getOrderedProducts(user.id)
            _orderedProductList.value=productUseCase
        }
    }
    fun addToOrderedProducts(userId: Int, productId: Int){
        viewModelScope.launch {
            Log.d("the product ViewModel is called","this is called")
            OrderUseCase().addOrderedProduct(userId,productId)

        }
    }
}