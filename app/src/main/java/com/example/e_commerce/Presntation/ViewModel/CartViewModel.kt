package com.example.e_commerce.Presntation.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.UseCase.CartUseCase
import com.example.e_commerce.Domain.UseCase.ProductUseCase
import kotlinx.coroutines.launch

class CartViewModel:ViewModel() {
    private val _cartProductList = MutableLiveData<List<Product>>()
    val cartProductList: LiveData<List<Product>> = _cartProductList
     fun getCartProducts(user: User){
        viewModelScope.launch {
            Log.d("the product ViewModel is called","this is called")
            val productUseCase= CartUseCase().getProducts(user)
            _cartProductList.value=productUseCase
        }
    }
    fun addToCart(userId:Int,productId:Int,quantity:Int){
        viewModelScope.launch {

            CartUseCase().addToCart(userId=userId,productId=productId,quantity=quantity)

        }
    }
    fun removeFromCart(userId: Int,productId: Int){
        viewModelScope.launch {
            CartUseCase().removeItemFromCart(userId=userId,productId)
            _cartProductList.value=_cartProductList.value?.filter {product ->
                product.id !=productId
            }
            Log.d("removing item from the cart (viewModel Call) ",cartProductList.value.toString())

        }
    }

}