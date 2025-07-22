package com.example.e_commerce.Domain.RepoInterface

import com.example.e_commerce.Data.Model.PaymentModel.BaiscConfirmPaymentIntentParams
import com.example.e_commerce.Data.Model.PaymentModel.StripeCardDto
import com.example.e_commerce.Domain.Entity.Card

interface PaymentRepository {
    suspend fun pay(userEmail:String, userName:String, card: Card, amount: Double, onResult:(Boolean, BaiscConfirmPaymentIntentParams?)->(Unit))
}