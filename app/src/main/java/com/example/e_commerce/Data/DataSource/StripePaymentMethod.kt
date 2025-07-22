package com.example.e_commerce.Data.DataSource

import android.util.Log
import com.example.e_commerce.Data.Model.PaymentModel.BaiscConfirmPaymentIntentParams
import com.example.e_commerce.Data.Model.PaymentModel.StripeCardDto
import com.example.e_commerce.MyApplication
import com.example.e_commerce.Data.Model.PaymentModel.RetrofitClient
import com.google.gson.JsonObject
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.model.PaymentMethodCreateParams
import retrofit2.Call
import kotlin.math.roundToInt

class StripePaymentMethod:IPaymentMethod {
    val pk="pk_test_51R5Rl0RpAzDeym7cVTvgXPngjLQj9znwwycouRFERAbjLehwqOJHK30YC4licTEf13BmUjQToTSjz4UKv2hp2dnu008ZM4R3V0"
    val stripe= Stripe(MyApplication.getAppContext(),pk)

    override fun pay(userEmail:String,userName:String,card: StripeCardDto, amount: Double, onResult: (Boolean,BaiscConfirmPaymentIntentParams?) -> Unit) {
        createPaymentMethod(userEmail,userName,card,amount){success,basicConfirmPaymentParams->
            onResult(success,basicConfirmPaymentParams)

        }

    }
    private fun createPaymentMethod(userEmail:String,userName:String,stripeCard: StripeCardDto, amount: Double, onResult: (Boolean,BaiscConfirmPaymentIntentParams?) -> Unit){
        val cardParams = PaymentMethodCreateParams.Card.Builder()
            .setNumber(stripeCard.number)
            .setCvc(stripeCard.cvc)
            .setExpiryMonth(stripeCard.expMonth)
            .setExpiryYear(stripeCard.expYear)
            .build()

        val billingDetails = PaymentMethod.BillingDetails.Builder()
            .setEmail(userEmail)
            .setName(userName)
            .build()

        val params = PaymentMethodCreateParams.create(cardParams, billingDetails)
        stripe.createPaymentMethod(params, callback =
        object : ApiResultCallback<PaymentMethod> {
            override fun onError(e: Exception) {
                onResult(false,null)
            }

            override fun onSuccess(result: PaymentMethod) {
                createPaymentIntent(result, amount){success,basicConfirmPaymentParams->
                    if (success) {
                        onResult(true,basicConfirmPaymentParams)
                    } else {
                        onResult(false,null)
                    }
                }                }
        })
    }

    private fun createPaymentIntent(paymentMethodParams: PaymentMethod, amount: Double,onResult: (Boolean,BaiscConfirmPaymentIntentParams?) -> Unit ) {
        Log.d("the response : ", paymentMethodParams.toString())
        val params: MutableMap<String, String> = mutableMapOf(
            "amount" to ((amount * 100).roundToInt().toString()),
            "currency" to "usd",
            "payment_method" to (paymentMethodParams.id ?: "")
        )

        val call = RetrofitClient.stripeApi.createPaymentIntent(params)
        call.enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: retrofit2.Response<JsonObject>
            ) {
                Log.e(
                    "PaymentAPI",
                    "response body: ${response.body()}"
                )
                if (response.isSuccessful) {
                    val clientSecret = response.body()?.get("client_secret")?.asString

                    onResult(true,BaiscConfirmPaymentIntentParams(paymentMethodParams.id!!,clientSecret!!))

                } else {
                    Log.e(
                        "PaymentAPI",
                        "Error Code: ${response.code()}, Error: ${response.errorBody()?.string()}"
                    )
                    onResult(false,null)


                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("PaymentAPI", "Request failed: ${t.message}")

                onResult(false,null)

            }
        })

    }
}