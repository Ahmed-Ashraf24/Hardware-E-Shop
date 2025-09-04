package com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.PaymentScreen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.e_commerce.Data.DataSource.StripePaymentMethod
import com.example.e_commerce.Domain.Entity.Card
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Presntation.ViewModel.PaymentViewModel
import com.example.e_commerce.R
import com.example.e_commerce.databinding.ActivityPaymentBinding
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams

class PaymentActivity : AppCompatActivity() {
    lateinit var binding:ActivityPaymentBinding
    val paymentViewModel=PaymentViewModel(StripePaymentMethod())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val product:Product= intent.getParcelableExtra("Product")!!
        binding=ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this)
            .load(product.imageURL)
            .into(binding.productImage)
        binding.productName.text=product.name
        binding.productPrice.text="${product.price}$ USD"
        binding.totalCost.text="${product.price}$ USD"

        val pk="Put Your API Key Here"
        val stripe= Stripe(this,pk)
        binding.payButton.setOnClickListener {
            val cardNumber=binding.cardNumber.text.toString()
            val userName=binding.cardHolderName.text.toString()
            val cardExpMon=binding.monthExp.text.toString().toInt()
            val cardExpYear=binding.yearExp.text.toString().toInt()
            val cardCvc=binding.cvc.text.toString()
            val userEmail=intent.getStringExtra("Email")
            val card= Card(number =cardNumber, expYear = cardExpYear, expMonth = cardExpMon, cvc = cardCvc )
        paymentViewModel.makePayment(userEmail = userEmail!!, userName = userName, card = card, amount = product.price.toDouble())
        }
        paymentViewModel.paymentStatus.observe(this){pair->
            if(pair.first){
                stripe.confirmPayment(this,
                    ConfirmPaymentIntentParams.createWithPaymentMethodId(clientSecret = pair.second.clientSecret,
                        paymentMethodId = pair.second.paymentMethodId
                        )
                    )
                Toast.makeText(this,"the transaction completed",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"there is a problem in the transaction",Toast.LENGTH_LONG).show()

            }
        }
    }
}