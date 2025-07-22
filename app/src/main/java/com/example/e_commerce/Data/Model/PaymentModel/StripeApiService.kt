package com.example.e_commerce.Data.Model.PaymentModel

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
private const val sk="put your secret key here"

interface StripeApiService {
    @Headers(
        "Authorization: Bearer $sk",

    )
    @FormUrlEncoded
    @POST("payment_intents")
    fun createPaymentIntent(
        @FieldMap params: Map<String, String>
    ): Call<JsonObject>

    @Headers(
        "Authorization: Bearer $sk",
    )
    @FormUrlEncoded
    @POST("tokens")
    fun createToken(@FieldMap params: Map<String, String>): Call<JsonObject>

}