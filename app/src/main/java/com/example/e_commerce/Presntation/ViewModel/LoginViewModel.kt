package com.example.e_commerce.Presntation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.UseCase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState = MutableLiveData<Result<User>>()
    val loginState: LiveData<Result<User>> = _loginState

    private val _emailState = MutableLiveData<Boolean>()
    val emailState: LiveData<Boolean> get() = _emailState

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val user = LoginUseCase().login(email, password)

                _loginState.value = user!!.let { Result.success(it) }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
