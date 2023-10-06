package com.pixelark.capstoneproject.ui.home

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    override fun onFragmentStarted() {
        binding.fragmentHomeButtonTry.setOnClickListener {
            viewModel.getSaleProducts()
        }
        viewModel.saleProductsData.observe(this) {

        }
    }
}