package com.example.e_commerce.Data.Model.PaymentModel

data class StripeCardDto(
    val number: String,
    val expMonth: Int,
    val expYear: Int,
    val cvc: String
)

