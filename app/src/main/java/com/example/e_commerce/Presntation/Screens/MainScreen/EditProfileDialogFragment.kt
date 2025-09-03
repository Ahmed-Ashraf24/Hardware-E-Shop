package com.example.e_commerce.Presntation.Screens.MainScreen

import android.R
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import  com.example.e_commerce.databinding.DialogEditProfileBinding

class EditProfileDialogFragment : DialogFragment() {

    interface OnProfileUpdateListener {
        fun onProfileUpdated(name: String, email: String, phone: String, address: String, gender: String)
    }

    private var updateListener: OnProfileUpdateListener? = null
    private var _binding: DialogEditProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_NAME = "name"
        private const val ARG_EMAIL = "email"
        private const val ARG_PHONE = "phone"
        private const val ARG_ADDRESS = "address"
        private const val ARG_GENDER = "gender"

        fun newInstance(
            name: String,
            email: String,
            phone: String,
            address: String,
            gender: String
        ): EditProfileDialogFragment {
            val fragment = EditProfileDialogFragment()
            val args = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_EMAIL, email)
                putString(ARG_PHONE, phone)
                putString(ARG_ADDRESS, address)
                putString(ARG_GENDER, gender)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogEditProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genderOptions = listOf("Male", "Female")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, genderOptions)
        binding.etGender.setAdapter(adapter)
        setupData()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupData() {
        arguments?.let { args ->
            binding.etName.setText(args.getString(ARG_NAME, ""))
            binding.etEmail.setText(args.getString(ARG_EMAIL, ""))
            binding.etPhone.setText(args.getString(ARG_PHONE, ""))
            binding.etAddress.setText(args.getString(ARG_ADDRESS, ""))
            binding.etGender.setText(args.getString(ARG_GENDER, ""))
        }
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            if (validateInputs()) {
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val phone = binding.etPhone.text.toString().trim()
                val address = binding.etAddress.text.toString().trim()
                val gender = binding.etGender.text.toString().trim()

                updateListener?.onProfileUpdated(name, email, phone, address, gender)
                dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true


        binding.tilName.error = null
        binding.tilEmail.error = null
        binding.tilPhone.error = null
        binding.tilAddress.error = null
        binding.tilGender.error = null


        if (binding.etName.text.toString().trim().isEmpty()) {
            binding.tilName.error = "Name is required"
            isValid = false
        }


        val email = binding.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Please enter a valid email"
            isValid = false
        }

        // Validate phone (optional but format check if provided)
        val phone = binding.etPhone.text.toString().trim()
        if (phone.isNotEmpty() && !android.util.Patterns.PHONE.matcher(phone).matches()) {
            binding.tilPhone.error = "Please enter a valid phone number"
            isValid = false
        }

        return isValid
    }

    fun setOnProfileUpdateListener(listener: OnProfileUpdateListener) {
        this.updateListener = listener
    }
}