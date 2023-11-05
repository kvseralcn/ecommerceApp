package com.pixelark.capstoneproject.ui.forgotpassword.presentation

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentForgotPasswordBinding
import com.pixelark.capstoneproject.ui.forgotpassword.domain.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
    FragmentForgotPasswordBinding::inflate, ForgotPasswordViewModel::class.java
) {
    override fun onFragmentStarted() {
        binding.fragmentForgotPasswordBtnForgotPassword.setOnClickListener() {
            if (!binding.fragmentForgotPasswordEtEmail.editText?.text.isNullOrBlank()) {
                val auth = FirebaseAuth.getInstance()
                val userEmail = binding.fragmentForgotPasswordEtEmail.editText?.text.toString()

                auth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                        } else {
                        }
                    }

                findNavController().navigate(R.id.signInFragment)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.forgot_password_empty_email_warning),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}