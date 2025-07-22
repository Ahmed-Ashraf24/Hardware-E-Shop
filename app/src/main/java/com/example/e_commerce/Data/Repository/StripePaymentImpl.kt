package com.example.e_commerce.Data.Repository

import com.example.e_commerce.Data.DataSource.IPaymentMethod
import com.example.e_commerce.Data.DataSource.StripePaymentMethod
import com.example.e_commerce.Data.Mapper.CardMapper
import com.example.e_commerce.Data.Model.PaymentModel.BaiscConfirmPaymentIntentParams
import com.example.e_commerce.Domain.Entity.Card
import com.example.e_commerce.Domain.RepoInterface.PaymentRepository

class StripePaymentImpl:PaymentRepository {
    val stripPaymentMethod:IPaymentMethod=StripePaymentMethod()
    override suspend fun pay(userEmail:String, userName:String, card: Card, amount: Double, onResult:(Boolean,BaiscConfirmPaymentIntentParams?)->(Unit)) {
    stripPaymentMethod.pay(userEmail = userEmail,userName=userName, card = CardMapper.toStripeCard(card), amount = amount){success,basic->
        onResult(success,basic)
    }
    }
}