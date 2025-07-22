package com.example.e_commerce.Presntation.Screens.MainScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.Utilities.UIModule.Banner
import com.example.e_commerce.Utilities.UIModule.BottomBanner
import com.example.e_commerce.Utilities.UiAdapters.BannerAdapter
import com.example.e_commerce.Utilities.UiAdapters.BottomBannerAdapter
import com.example.e_commerce.Utilities.UiAdapters.ProductAdapter
import com.example.e_commerce.databinding.FragmentMainScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainScreenFragment : Fragment() {
    lateinit var binding: FragmentMainScreenBinding

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityInstnance = (activity as? MainScreen)
        val banners = listOf(
            Banner(
                "Free Shipping!\nDuring May - August 2021!", listOf(
                    R.drawable.sqa1yji_u5ztv10gkeorxly2,
                    R.drawable.sqa1yji_u5ztv10gkeorxly2,
                    R.drawable.sqa1yji_u5ztv10gkeorxly2
                )
            ),

            Banner(
                "Summer Sale!\nUp to 50% Off!", listOf(
                    R.drawable.sqa1yji_u5ztv10gkeorxly2,
                    R.drawable.sqa1yji_u5ztv10gkeorxly2,
                    R.drawable.sqa1yji_u5ztv10gkeorxly2
                )
            )
        )
        binding.bannerRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BannerAdapter(banners)
        }



        activityInstnance!!.productsViwModel.productList.observe(viewLifecycleOwner) { productList ->


            Log.d("there is data", productList.toString())
            binding.productsRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = ProductAdapter(
                    productList.groupBy {
                    it.category
                }.values.flatMap {
                        it.take(5)
                    }) { product ->

                    activityInstnance.product = product
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ItemPageFragment()).commit()
                }
            }
        }


        val bottomBanners = listOf(
            BottomBanner(
                R.drawable.sqa1yji_u5ztv10gkeorxly2, "C02 - Cable Multifunction"
            ),

            BottomBanner(
                R.drawable.sqa1yji_u5ztv10gkeorxly2, "C03 - Charger Adapter"
            )
        )
        binding.bottomBannerRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BottomBannerAdapter(bottomBanners)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = MainScreenFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}