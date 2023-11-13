package com.pixelark.capstoneproject.ui.signup.presentation

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentSignupBinding
import com.pixelark.capstoneproject.ui.signup.domain.SignUpViewModel
import com.pixelark.capstoneproject.validation.EmailInputValidation
import com.pixelark.capstoneproject.validation.EmailInputValidationData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding, SignUpViewModel>(
    FragmentSignupBinding::inflate, SignUpViewModel::class.java
) {
    override fun onFragmentStarted() {
        viewModel.signUpResult.observe(this) { dataOrException ->
            if (dataOrException.data == true) {
                findNavController().navigate(R.id.homeFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    dataOrException.e?.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.fragmentSignUpBtnSignUp.setOnClickListener {
            var name = binding.fragmentSignUpEtName.editText?.text.toString()
            val email = binding.fragmentSignUpEtEmail.editText?.text.toString()
            val password = binding.fragmentSignUpEtPassword.editText?.text.toString()
            val confirmPassword = binding.fragmentSignUpEtConfirmPassword.editText?.text.toString()

            if (name.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty), Toast.LENGTH_SHORT
                ).show()
            }
            //TODO: bu kodu passwordInputvalidatına yaz
            else if (password != confirmPassword) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_mismatch_error), Toast.LENGTH_SHORT
                ).show()
            } else if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_too_short), Toast.LENGTH_SHORT
                ).show()
            } else if (confirmPassword.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_empty), Toast.LENGTH_SHORT
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_empty), Toast.LENGTH_SHORT
                ).show()
            } else {
                val emailValidationResult =
                    EmailInputValidation.validate(email, EmailInputValidationData("Error"))
                if (emailValidationResult.isSuccess && password.length >= 6) {
                    viewModel.signUp(email, password, name)
                } else {
                    Toast.makeText(
                        requireContext(),
                        emailValidationResult.errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}