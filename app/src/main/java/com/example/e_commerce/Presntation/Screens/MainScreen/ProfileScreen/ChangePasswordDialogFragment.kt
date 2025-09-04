package com.example.e_commerce.Presntation.Screens.MainScreen.ProfileScreen
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.e_commerce.R
import com.example.e_commerce.databinding.DialogChangePasswordBinding

class ChangePasswordDialogFragment : DialogFragment() {

    interface OnPasswordChangeListener {
        fun onPasswordChanged(currentPassword: String, newPassword: String)
    }

    private var passwordChangeListener: OnPasswordChangeListener? = null
    lateinit var binding:DialogChangePasswordBinding
    private var isCurrentPasswordVisible = false
    private var isNewPasswordVisible = false
    private var isConfirmPasswordVisible = false

    companion object {
        fun newInstance(): ChangePasswordDialogFragment {
            return ChangePasswordDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DialogChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupPasswordVisibilityToggles()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupClickListeners() {
        binding.btnChangePassword.setOnClickListener {
            if (validatePasswords()) {
                val currentPassword = binding.etCurrentPassword.text.toString()
                val newPassword = binding.etNewPassword.text.toString()

                passwordChangeListener?.onPasswordChanged(currentPassword, newPassword)
                dismiss()
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun setupPasswordVisibilityToggles() {
        binding.ivToggleCurrentPassword.setOnClickListener {
            isCurrentPasswordVisible = !isCurrentPasswordVisible
            togglePasswordVisibility(binding.etCurrentPassword, binding.ivToggleCurrentPassword, isCurrentPasswordVisible)
        }

        binding.ivToggleNewPassword.setOnClickListener {
            isNewPasswordVisible = !isNewPasswordVisible
            togglePasswordVisibility(binding.etNewPassword, binding.ivToggleNewPassword, isNewPasswordVisible)
        }

        binding.ivToggleConfirmPassword.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            togglePasswordVisibility(binding.etConfirmPassword, binding.ivToggleConfirmPassword, isConfirmPasswordVisible)
        }
    }

    private fun togglePasswordVisibility(editText: com.google.android.material.textfield.TextInputEditText, imageView: ImageView, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.baseline_visibility_off_24)
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.baseline_visibility_24)
        }
        editText.setSelection(editText.text?.length ?: 0)
    }

    private fun validatePasswords(): Boolean {
        var isValid = true

        binding.tilCurrentPassword.error = null
        binding.tilNewPassword.error = null
        binding.tilConfirmPassword.error = null

        val currentPassword = binding.etCurrentPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (currentPassword.isEmpty()) {
            binding.tilCurrentPassword.error = "Current password is required"
            isValid = false
        }

        if (newPassword.isEmpty()) {
            binding.tilNewPassword.error = "New password is required"
            isValid = false
        } else if (newPassword.length < 6) {
            binding.tilNewPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else if (newPassword == currentPassword) {
            binding.tilNewPassword.error = "New password must be different from current password"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Please confirm your new password"
            isValid = false
        } else if (confirmPassword != newPassword) {
            binding.tilConfirmPassword.error = "Passwords do not match"
            isValid = false
        }

        return isValid
    }

    fun setOnPasswordChangeListener(listener: OnPasswordChangeListener) {
        this.passwordChangeListener = listener
    }
}