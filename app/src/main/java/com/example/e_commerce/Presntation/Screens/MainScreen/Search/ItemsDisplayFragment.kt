package com.example.e_commerce.Presntation.Screens.MainScreen.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.Presntation.Screens.MainScreen.HomeScreen.HomePage.MainScreenFragment
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.R
import com.example.e_commerce.Utilities.UiAdapters.FilteredProductsAdapter
import com.example.e_commerce.databinding.FragmentItemsDisplayBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemsDisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsDisplayFragment : Fragment() {
    lateinit var binding: FragmentItemsDisplayBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentItemsDisplayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityInstance = (activity as? MainScreen)
        binding.categoryTitle.text=activityInstance!!.selectedCategory
        activityInstance!!.productsViwModel.productList.observe(viewLifecycleOwner) { productList ->
            binding.productsRecycler.layoutManager = LinearLayoutManager(requireContext())
            with(productList) {
                binding.productsRecycler.adapter =
                    FilteredProductsAdapter(this.filter { it.category == activityInstance.selectedCategory }
                   , onAddToCartClick = {product->

                       activityInstance.cartViewModel.addToCart(activityInstance.user!!, productId =product.id,1)} )
            }

        }
        binding.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container,MainScreenFragment()).commit()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemsDisplayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemsDisplayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}