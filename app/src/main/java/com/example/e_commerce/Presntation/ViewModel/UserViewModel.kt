package com.example.e_commerce.Presntation.ViewModel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.UseCase.UserUseCase
import com.example.e_commerce.Utilities.PasswordUtilities
import kotlinx.coroutines.launch

class UserViewModel(
    private val updateUserProfileUseCase: UserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun loadUser(user: User) {
        _user.value = user
    }

    @SuppressLint("SuspiciousIndentation")
    fun updateUser(user: User) {
        viewModelScope.launch {
          val (userId,userName,userEmail,userAddress,userPhone,userGender)=user
            updateUserProfileUseCase.editUserInfo(userId,userName,userEmail,userAddress,userPhone)
            _user.value = user
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun changePassword(userId:Int, newPassword:String){
        val salt=PasswordUtilities.generateSalt()
        val hashedPassword=PasswordUtilities.hashPassword(newPassword,salt)
        viewModelScope.launch {
            updateUserProfileUseCase.changeUserPassword(userId,hashedPassword,salt)
        }
    }
}