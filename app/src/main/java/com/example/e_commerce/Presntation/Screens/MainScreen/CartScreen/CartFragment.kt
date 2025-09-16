package com.example.e_commerce.Presntation.Screens.MainScreen.CartScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.PaymentScreen.PaymentPageFragment
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.R
import com.example.e_commerce.Utilities.UIModule.CartItem
import com.example.e_commerce.Utilities.UiAdapters.CartAdapter
import com.example.e_commerce.databinding.FragmentCartBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding:FragmentCartBinding
    private var totalItemsSubCount =0f

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
        binding=FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityInstance=(activity as? MainScreen)
      binding.favouritesRecyclerView.layoutManager=LinearLayoutManager(requireContext())
      activityInstance!!.cartViewModel.getCartProducts(user = activityInstance.user!!)
        if(activityInstance.cartViewModel.cartProductList.value==null){
            binding.subtotalAmount.text="0"
            binding.totalAmount.text="0"
            binding.taxAmount.text="0"
            binding.shippingAmount.text="0"
            binding.orderAllButton.isEnabled=false
        }
        activityInstance.cartViewModel.cartProductList.observe(viewLifecycleOwner){productList->
            binding.orderAllButton.isEnabled=true
            Log.d("the data in cartViewModel is changed (call from the activity)",productList.toString())
            productList.forEach { product->
                totalItemsSubCount+=product.price
                binding.subtotalAmount.text=totalItemsSubCount.toString()

            }
            if(totalItemsSubCount!=0f) {
                binding.totalAmount.text = (totalItemsSubCount + 27.99).toString()
            }
            val adapter=CartAdapter(cartList = productList.map { CartItem(it,1) },{price->
                totalItemsSubCount+=price
                binding.subtotalAmount.text=totalItemsSubCount.toString()
                binding.totalAmount.text=(totalItemsSubCount+27.99).toString()
            },
                {product,quantity->
                    activityInstance.cartViewModel.removeFromCart(productId=product.id, userId = activityInstance.user!!.id)
                    totalItemsSubCount-=product.price*quantity
                    binding.subtotalAmount.text=totalItemsSubCount.toString()
                    binding.totalAmount.text=(totalItemsSubCount+27.99).toString()
                    if(activityInstance.cartViewModel.cartProductList.value==null){
                        binding.taxAmount.text="0"
                        binding.shippingAmount.text="0"
                        binding.totalAmount.text="0"
                        binding.orderAllButton.isEnabled=false
                    }
                    binding.totalAmount.text=(totalItemsSubCount+27.99).toString()


            }
            )


            binding.favouritesRecyclerView.adapter=adapter

        }
        binding.orderAllButton.setOnClickListener {
       val cartList= (binding.favouritesRecyclerView.adapter as CartAdapter).getCartListProducts()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container,PaymentPageFragment(cartList,totalItemsSubCount.toDouble())).commit()
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}