package com.pixelark.capstoneproject.ui.cart.presentation

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.adapter.CartAdapter
import com.pixelark.capstoneproject.adapter.CartClickListener
import com.pixelark.capstoneproject.adapter.CartDeleteClickListener
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ClearCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FragmentCartBinding
import com.pixelark.capstoneproject.ui.cart.domain.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(
    FragmentCartBinding::inflate, CartViewModel::class.java
) {
    private lateinit var cartAdapter: CartAdapter
    fun initializeCartAdapter(adapter: CartAdapter) {
        cartAdapter = adapter
    }

    override fun onFragmentStarted() {
        binding.fragmentCartRvCartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        uid?.let { viewModel.getCartProducts(it) }
        viewModel.cartProductsData.observe(this) { response ->
            setCartAdapter(response.products)

            if (response.products.isNotEmpty()) {
                setAmounts()
            } else {
                clearPaymentData()
            }
            binding.fragmentCartBtnPayment.setOnClickListener {
                val action =
                    CartFragmentDirections.actionCartFragmentToPaymentFragment()
                findNavController().navigate(action)
            }

            Toast.makeText(
                requireContext(),
                response.message,
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.fragmentCartIbDeleteAll.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                viewModel.deleteAllProducts(ClearCartRequest(user.uid))
            }
            clearPaymentData()
        }

        viewModel.deleteAllProductsData.observe(requireActivity()) { response ->
            if (response.status == 200) {
                if (::cartAdapter.isInitialized) {
                    cartAdapter.clearList()
                }
            }
        }
    }

    private fun setAmounts() {
        val shipping = 0.125
        var totalPayment = 0.0
        val convertedValue = shipping * 1000
        totalPayment = cartAdapter.getTotalAmount() + shipping
        binding.fragmentCartTvSubTotal.text = cartAdapter.getTotalAmount().toString()
        binding.fragmentCartTvShipping.text = convertedValue.toInt().toString()
        binding.fragmentCartTvTotalPayment.text = totalPayment.toString()
    }

    private fun clearPaymentData() {
        binding.fragmentCartTvSubTotal.text = "0.0"
        binding.fragmentCartTvShipping.text = "0.0"
        binding.fragmentCartTvTotalPayment.text = "0.0"
    }

    private fun setCartAdapter(cartProductList: MutableList<ProductModel>) {
        cartAdapter =
            CartAdapter(cartProductList, object : CartClickListener {
                override fun onClick(selectedProduct: ProductModel) {
                    val action =
                        CartFragmentDirections.actionCartFragmentToProductDetailFragment(
                            selectedProduct.id
                        )
                    findNavController().navigate(action)
                }
            },
                object : CartDeleteClickListener {
                    override fun onClickDelete(selectedProduct: ProductModel) {
                        viewModel.deleteProducts(DeleteFromCartRequest(selectedProduct.id))
                        viewModel.deleteProductsData.observe(requireActivity()) { response ->
                            Toast.makeText(
                                requireContext(),
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        setAmounts()
                    }
                })
        binding.fragmentCartRvCartRecyclerView.adapter = cartAdapter
    }
}