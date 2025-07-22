package com.example.e_commerce.Presntation.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.UseCase.ProductUseCase
import kotlinx.coroutines.launch

class ProductsViwModel:ViewModel() {
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList
    fun getAllProducts(){
        viewModelScope.launch {
            Log.d("the product ViewModel is called","this is called")
        val productUseCase=ProductUseCase().getAllProducts()
        _productList.value=productUseCase
        }
    }

}