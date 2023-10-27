package com.pixelark.capstoneproject.ui.paymentcomplete.presentation

import androidx.navigation.fragment.findNavController
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
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

            //TODO ödeme tamamlanınca payment sayfasında sepeti temizle
            //TODO payment sayfasında ay yıl girerken klavyeyi kapat
            //TODO sayfada geri gelme durumlarını kontrol et anasayfada geri gelirse uygulamayı kapatsın
        }
    }
}