package com.pixelark.capstoneproject.ui.paymentcomplete.presentation

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ClearCartRequest
import com.pixelark.capstoneproject.databinding.FragmentSuccessBinding
import com.pixelark.capstoneproject.ui.paymentcomplete.domain.PaymentCompleteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentCompleteFragment : BaseFragment<FragmentSuccessBinding, PaymentCompleteViewModel>(
    FragmentSuccessBinding::inflate, PaymentCompleteViewModel::class.java
) {
    override fun onFragmentStarted() {
        binding.fragmentSuccessBtnPayment.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)

            clearCart()
        }
    }

    private fun clearCart() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            viewModel.deleteAllProducts(ClearCartRequest(user.uid))
        }

        viewModel.deleteAllProductsData.observe(requireActivity()) { response ->
            if (response.status == 200) {
                Log.d("TAG", "Sepet temizlendi.")
            }
        }
    }
}