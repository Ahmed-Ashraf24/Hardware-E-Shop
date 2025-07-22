package com.example.e_commerce.Data.Model.PaymentModel

data class BaiscConfirmPaymentIntentParams(
    val paymentMethodId:String,
    val clientSecret:String
)
