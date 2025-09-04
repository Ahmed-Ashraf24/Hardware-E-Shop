package com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.ItemPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.PaymentScreen.PaymentPageFragment
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentItemPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [itemPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
lateinit var binding:FragmentItemPageBinding
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
        binding = FragmentItemPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val activityInstance=(activity as? MainScreen)
        val product=activityInstance?.product
        Glide.with(this)
            .load(product!!.imageURL)
            .into(binding.productImage)
        binding.productTitle.text=product.name
        binding.productPrice.text="${product.price}$ USD"
        binding.descriptionContent.text=product.description
        binding.orderButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PaymentPageFragment())
                .commit()

        }
        binding.cartButton.setOnClickListener {
            Log.d("item page","button was clicked")
            activityInstance.cartViewModel.addToCart(userId = activityInstance.user!!.id, productId = product.id, quantity = 1)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment itemPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}