package com.example.e_commerce.Presntation.Screens.MainScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.e_commerce.Data.DataSource.StripePaymentMethod
import com.example.e_commerce.Domain.Entity.Card
import com.example.e_commerce.Presntation.ViewModel.OrderViewModel
import com.example.e_commerce.Presntation.ViewModel.PaymentViewModel
import com.example.e_commerce.databinding.FragmentPaymentPageBinding
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PaymentPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentPaymentPageBinding
    val paymentViewModel= PaymentViewModel(StripePaymentMethod())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentPaymentPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityInstance=(activity as? MainScreen)
        val product=activityInstance!!.product
        val user=activityInstance.user
        val orderViewModel= activityInstance.orderViewModel

        Glide.with(this)
            .load(product!!.imageURL)
            .into(binding.productImage)
        binding.productName.text=product.name
        binding.productPrice.text="${product.price}$ USD"
        binding.totalCost.text="${product.price}$ USD"

        val pk="pk_test_51R5Rl0RpAzDeym7cVTvgXPngjLQj9znwwycouRFERAbjLehwqOJHK30YC4licTEf13BmUjQToTSjz4UKv2hp2dnu008ZM4R3V0"

        val stripe= Stripe(requireContext(),pk)
        binding.payButton.setOnClickListener {
            val cardNumber=binding.cardNumber.text.toString()
            val userName=binding.cardHolderName.text.toString()
            val cardExpMon=binding.monthExp.text.toString().toInt()
            val cardExpYear=binding.yearExp.text.toString().toInt()
            val cardCvc=binding.cvc.text.toString()
            val userEmail=activityInstance.user!!.email
            val card= Card(number =cardNumber, expYear = cardExpYear, expMonth = cardExpMon, cvc = cardCvc )
            paymentViewModel.makePayment(userEmail = userEmail!!, userName = userName, card = card, amount = product.price.toDouble())
        }
        paymentViewModel.paymentStatus.observe(viewLifecycleOwner){pair->
            if(pair.first){
                stripe.confirmPayment(this,
                    ConfirmPaymentIntentParams.createWithPaymentMethodId(clientSecret = pair.second.clientSecret,
                        paymentMethodId = pair.second.paymentMethodId
                    )
                )
                orderViewModel.addToOrderedProducts(userId =user!!.id, productId = product.id)

                Toast.makeText(requireContext(),"the transaction completed", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(requireContext(),"there is a problem in the transaction", Toast.LENGTH_LONG).show()

            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PaymentPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}