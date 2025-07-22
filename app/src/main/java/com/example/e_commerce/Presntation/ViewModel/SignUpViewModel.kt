package com.example.e_commerce.Presntation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Domain.UseCase.SignUpUseCase
import com.example.e_commerce.Utilities.Constants

class SignUpViewModel : ViewModel() {
    private val _registerState = MutableLiveData<Result<Unit>>()
    val registerState: LiveData<Result<Unit>> = _registerState
    private val _emailState = MutableLiveData<Boolean>()
    val emailState: LiveData<Boolean> get() = _emailState

    suspend fun register(user: UserEntity):Boolean {
        val isEmailValid= Constants.isValidEmail(user.email)
        if(isEmailValid){
            return try {
                SignUpUseCase().signUp(user)
                _registerState.value = Result.success(Unit)
                true
            } catch (e: Exception) {
                _registerState.value = Result.failure(e)
                false
            }
        }
        else{
            return false
        }
    }
}
