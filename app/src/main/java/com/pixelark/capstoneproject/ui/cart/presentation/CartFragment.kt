package com.pixelark.capstoneproject.ui.cart.presentation

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.adapter.CartAdapter
import com.pixelark.capstoneproject.adapter.CartClickListener
import com.pixelark.capstoneproject.adapter.CartDeleteClickListener
import com.pixelark.capstoneproject.core.BaseFragment
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

    override fun onFragmentStarted() {
        binding.fragmentCartRvCartRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val uid = FirebaseAuth.getInstance().currentUser?.uid
        uid?.let { viewModel.getCartProducts(it) }
        viewModel.cartProductsData.observe(this) { response ->
            setCartAdapter(response.products)

            Toast.makeText(
                requireContext(),
                response.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setCartAdapter(cartProductList: MutableList<ProductModel>) {
        cartAdapter =
            CartAdapter(cartProductList, object : CartClickListener {
                override fun onClick(selectedProduct: ProductModel) {
                }
            },
                object : CartDeleteClickListener {
                    override fun onClickDelete(selectedProduct: ProductModel) {
                        viewModel.deleteProducts(DeleteFromCartRequest(selectedProduct.id))
                        viewModel.cartData.observe(requireActivity()) { response ->

                            Toast.makeText(
                                requireContext(),
                                response.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        binding.fragmentCartRvCartRecyclerView.adapter = cartAdapter
    }
}