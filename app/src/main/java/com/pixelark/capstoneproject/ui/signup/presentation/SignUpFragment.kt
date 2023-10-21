package com.pixelark.capstoneproject.ui.signup.presentation

import android.widget.Toast
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
        viewModel.authResult.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error: ${it.exceptionOrNull()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            var name = binding.fragmentSignUpEtName.editText?.text.toString()
            var surname = binding.fragmentSignUpEtSurname.editText?.text.toString()
            val email = binding.fragmentSignUpEtEmail.editText?.text.toString()
            val password = binding.fragmentSignUpEtPassword.editText?.text.toString()
            val confirmPassword = binding.fragmentSignUpEtConfirmPassword.editText?.text.toString()
            if (password != confirmPassword) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_mismatch_error), Toast.LENGTH_SHORT
                ).show()
            }
            if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_too_short), Toast.LENGTH_SHORT
                ).show()
            }
            val emailValidationResult =
                EmailInputValidation.validate(email, EmailInputValidationData("Error"))
            if (emailValidationResult.isSuccess) {
                viewModel.signUp(email, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    emailValidationResult.errorMessage, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}