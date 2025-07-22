package com.example.e_commerce.Data.Mapper

import com.example.e_commerce.Data.Model.PaymentModel.StripeCardDto
import com.example.e_commerce.Domain.Entity.Card

class CardMapper {
    companion object {
        fun toCard(stripeCard: StripeCardDto): Card {
            return Card(
                number = stripeCard.number,
                expMonth = stripeCard.expMonth,
                expYear = stripeCard.expYear,
                cvc = stripeCard.cvc
            )
        }

        fun toStripeCard(card: Card): StripeCardDto {
            return StripeCardDto(
                number = card.number,
                expMonth = card.expMonth,
                expYear = card.expYear,
                cvc = card.cvc
            )
        }
    }
}