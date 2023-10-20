package com.pixelark.capstoneproject.ui.paymentcomplete.presentation

import androidx.navigation.fragment.findNavController
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentSuccessBinding
import com.pixelark.capstoneproject.ui.paymentcomplete.domain.PaymentCompleteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentCompleteFragment : BaseFragment<FragmentSuccessBinding, PaymentCompleteViewModel>(
    FragmentSuccessBinding::inflate, PaymentCompleteViewModel::class.java
) {
    override fun onFragmentStarted() {
        binding.fragmentPaymentBtnPayment.setOnClickListener {
            val action =
                PaymentCompleteFragmentDirections.actionPaymentCompleteFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
}