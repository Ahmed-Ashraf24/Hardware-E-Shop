package com.example.e_commerce.Data.Model.PaymentModel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.stripe.com/v1/"

    val stripeApi: StripeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeApiService::class.java)
    }
}