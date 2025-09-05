package com.example.e_commerce.Presntation.Screens.MainScreen.ProfileScreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.e_commerce.Data.Repository.UserRepoImp
import com.example.e_commerce.Domain.Entity.User
import com.example.e_commerce.Domain.UseCase.UserUseCase
import com.example.e_commerce.Presntation.Screens.Auth.LoginActivity
import com.example.e_commerce.Presntation.Screens.MainScreen.MainScreen
import com.example.e_commerce.Presntation.ViewModel.UserViewModel
import com.example.e_commerce.databinding.FragmentAcountBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AcountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AcountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentAcountBinding
    private val userViewModel= UserViewModel(UserUseCase(UserRepoImp()))
    private val user:User?
        get()=(activity as? MainScreen)?.user
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
        binding = FragmentAcountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityInstance = (activity as? MainScreen)

        setupUserData(user)
        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditProfile.setOnClickListener {
            val (userId,userName,userEmail,userAddress,userPhone,userGender)=activityInstance!!.user!!

            val dialog= EditProfileDialogFragment.newInstance(
                userName, userEmail, userPhone, userAddress, userGender
            )
            dialog.apply {
                setOnProfileUpdateListener(object:
                    EditProfileDialogFragment.OnProfileUpdateListener {
                    override fun onProfileUpdated(
                        name: String,
                        email: String,
                        phone: String,
                        address: String,
                        gender: String
                    ) {
                        userViewModel.loadUser(user!!)
                        userViewModel.updateUser(User(user!!.id,name,email,address,phone,gender))

                        Log.d("user data after editing it from acount fragment",user.toString())
                    }

                })
            }
            userViewModel.user.observe(viewLifecycleOwner){user->
                setupUserData(user)
            activityInstance.user=user
            }
            dialog.show(parentFragmentManager, "EditProfileDialog")

        }
        binding.btnChangePassword.setOnClickListener {
            val dialog=ChangePasswordDialogFragment.newInstance()
            dialog.apply {
                setOnPasswordChangeListener(object :ChangePasswordDialogFragment.OnPasswordChangeListener{
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onPasswordChanged(currentPassword: String, newPassword: String) {
                        userViewModel.changePassword(activityInstance!!.user!!.id,newPassword)
                    }

                })
            }
            dialog.show(parentFragmentManager,"changePasswordDialog")
        }
        if (activityInstance!!.orderViewModel.orderedProductList.value == null ||
            activityInstance.cartViewModel.cartProductList.value == null
        ) {
            activityInstance.orderViewModel.getOrderedProducts(activityInstance.user!!)
            activityInstance.cartViewModel.getCartProducts(activityInstance.user!!)
            activityInstance.orderViewModel.orderedProductList.observe(viewLifecycleOwner){productlist->
                binding.tvTotalOrders.text =productlist.size.toString()

            }
            activityInstance.cartViewModel.cartProductList.observe(viewLifecycleOwner){productlist->
                binding.tvCartItems.text =productlist.size.toString()

            }

        } else {
            binding.tvTotalOrders.text =
                activityInstance.orderViewModel.orderedProductList.value!!.size.toString()
            binding.tvCartItems.text =
                activityInstance.cartViewModel.cartProductList.value!!.size.toString()
        }


    }

    private fun setupUserData(user: User?) {
        binding.tvUserName.text = user?.name
        binding.tvPhoneNumber.text = user?.phone
        binding.tvAddress.text = user?.address
        binding.tvUserEmail.text = user?.email
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AcountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AcountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}