package com.example.e_commerce.Presntation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.Data.DataSource.IPaymentMethod
import com.example.e_commerce.Data.Mapper.CardMapper
import com.example.e_commerce.Data.Model.PaymentModel.BaiscConfirmPaymentIntentParams
import com.example.e_commerce.Data.Model.PaymentModel.StripeCardDto
import com.example.e_commerce.Domain.Entity.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val paymentMethod: IPaymentMethod
) : ViewModel() {

    private val _paymentStatus = MutableLiveData<Pair<Boolean,BaiscConfirmPaymentIntentParams>>()
    val paymentStatus: LiveData<Pair<Boolean,BaiscConfirmPaymentIntentParams>> get() = _paymentStatus

    fun makePayment(
        userEmail: String,
        userName: String,
        card: Card,
        amount: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            paymentMethod.pay(userEmail, userName, CardMapper.toStripeCard(card), amount) { success,basicConfirmPaymentParams->
                _paymentStatus.postValue(Pair(success,basicConfirmPaymentParams!!))
            }
        }
    }
}