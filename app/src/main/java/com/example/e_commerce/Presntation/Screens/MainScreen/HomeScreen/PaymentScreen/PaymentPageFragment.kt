package com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.PaymentScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.Data.DataSource.StripePaymentMethod
import com.example.e_commerce.Domain.Entity.Card
import com.example.e_commerce.Domain.Entity.Product
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.Presntation.ViewModel.PaymentViewModel
import com.example.e_commerce.Utilities.UIModule.CartItem
import com.example.e_commerce.Utilities.UiAdapters.PaymentItemAdapter
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
class PaymentPageFragment(val listOfItems:List<CartItem>,val price:Double) : Fragment() {
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
        val user=activityInstance!!.user
        val orderViewModel= activityInstance.orderViewModel
        var cardList= mutableListOf<CartItem>()
        cardList.addAll(
            listOfItems
        )
        binding.itemCount.text= listOfItems.size.toString()+" items"
        binding.productsRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        binding.productsRecyclerView.adapter=PaymentItemAdapter(requireContext(),cardList)


        val pk="API_PK"
        val stripe= Stripe(requireContext(),pk)
        binding.payButton.setOnClickListener {
            val cardNumber=binding.cardNumber.text.toString()
            val userName=binding.cardHolderName.text.toString()
            val cardExpMon=binding.monthExp.text.toString().toInt()
            val cardExpYear=binding.yearExp.text.toString().toInt()
            val cardCvc=binding.cvc.text.toString()
            val userEmail=activityInstance.user!!.email
            val card= Card(number =cardNumber, expYear = cardExpYear, expMonth = cardExpMon, cvc = cardCvc )
            paymentViewModel.makePayment(userEmail = userEmail!!, userName = userName, card = card, amount = price)
        }
        binding.totalCost.text=price.toString()
        paymentViewModel.paymentStatus.observe(viewLifecycleOwner){pair->
            if(pair.first){
                stripe.confirmPayment(this,
                    ConfirmPaymentIntentParams.createWithPaymentMethodId(clientSecret = pair.second.clientSecret,
                        paymentMethodId = pair.second.paymentMethodId
                    )
                )
                listOfItems.forEach { cartItem ->
                    orderViewModel.addToOrderedProducts(userId =user!!.id, productId = cartItem.product.id)

                }

                Toast.makeText(requireContext(),"the transaction completed", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(requireContext(),"there is a problem in the transaction", Toast.LENGTH_LONG).show()

            }
        }
    }


}