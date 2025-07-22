package com.example.e_commerce.Data.DataSource

import com.example.e_commerce.Data.Model.DatabaseModel.UserEntity
import com.example.e_commerce.Data.Model.PaymentModel.BaiscConfirmPaymentIntentParams
import com.example.e_commerce.Data.Model.PaymentModel.StripeCardDto
import com.stripe.android.model.ConfirmPaymentIntentParams

interface IPaymentMethod {
    fun pay(userEmail:String,userName:String,card: StripeCardDto, amount: Double, onResult: (Boolean,BaiscConfirmPaymentIntentParams?) -> Unit)
}