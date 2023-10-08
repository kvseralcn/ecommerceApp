package com.pixelark.capstoneproject.ui.cart.presentation

import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.databinding.FragmentCartBinding
import com.pixelark.capstoneproject.ui.cart.domain.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(
    FragmentCartBinding::inflate, CartViewModel::class.java
) {
    override fun onFragmentStarted() {
    }
}