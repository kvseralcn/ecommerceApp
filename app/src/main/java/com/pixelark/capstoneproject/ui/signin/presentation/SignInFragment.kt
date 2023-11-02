package com.pixelark.capstoneproject.ui.signin.presentation

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentSigninBinding
import com.pixelark.capstoneproject.ui.signin.domain.SignInViewModel
import com.pixelark.capstoneproject.validation.EmailInputValidation
import com.pixelark.capstoneproject.validation.EmailInputValidationData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSigninBinding, SignInViewModel>(
    FragmentSigninBinding::inflate, SignInViewModel::class.java
) {
    override fun onFragmentStarted() {

        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(R.id.home_graph)
        }
        viewModel.authResult.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.homeFragment)
            } else {
                binding.fragmentSignInEtEmail.editText?.text = null
                binding.fragmentSignInEtPassword.editText?.text = null
                Toast.makeText(
                    requireContext(),
                    "Error: ${it.exceptionOrNull()}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.fragmentSignInBtnSignIn.setOnClickListener {
            val email = binding.fragmentSignInEtEmail.editText?.text.toString()
            val password = binding.fragmentSignInEtPassword.editText?.text.toString()
            val emailValidationResult = EmailInputValidation.validate(
                email,
                EmailInputValidationData(errorMessage = "E-mail hatalı")
            )
            if (password.length < 6) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.password_too_short), Toast.LENGTH_SHORT
                ).show()
            }
            if (emailValidationResult.isSuccess) {
                viewModel.signIn(email, password)
            } else {
                Toast.makeText(
                    requireContext(),
                    emailValidationResult.errorMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        binding.fragmentSignInIvContinueButton.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }
}