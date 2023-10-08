package com.pixelark.capstoneproject.ui.productdetail.presentation

import android.graphics.Paint
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.databinding.FragmentProductDetailsBinding
import com.pixelark.capstoneproject.ui.productdetail.domain.ProductDetailViewModel
import com.pixelark.capstoneproject.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailsBinding, ProductDetailViewModel>(
    FragmentProductDetailsBinding::inflate, ProductDetailViewModel::class.java
) {

    private val args by navArgs<ProductDetailFragmentArgs>()

    override fun onFragmentStarted() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateUp()
            }
        })
        viewModel.getProductDetail(args.productId)
        viewModel.productDetailData.observe(this) { response ->
            Glide.with(this)
                .load(response.product.imageOne)
                .into(binding.fragmentProductDetailsIvProductImage)
            binding.fragmentProductDetailsTvSaleProductTitle.text = response.product.title
            binding.fragmentProductDetailsTvSaleProductDescription.text =
                response.product.description
            binding.fragmentProductDetailsRbBar.rating = response.product.rate
            binding.fragmentProductDetailsTvSaleProductRate.text = response.product.rate.toString()

            binding.fragmentProductDetailsBtnBack.setOnClickListener {
                navigateUp()
            }
            binding.fragmentProductDetailsBtnFavorite.setOnClickListener { }



            when (response.product.count) {
                in 2..5 -> {
                    binding.fragmentProductDetailsTvSaleProductCount.text =
                        Constants.TextPicker.STOCK_ALMOST_EMPTY
                }

                1 -> {
                    binding.fragmentProductDetailsTvSaleProductCount.text =
                        Constants.TextPicker.LAST_ITEM_LEFT
                }

                else -> {
                    binding.fragmentProductDetailsTvSaleProductCount.text =
                        Constants.TextPicker.STOCK_AVAILABLE
                }
            }

            if (response.product.saleState == true) {
                binding.fragmentProductDetailsTvProductPrice.text =
                    response.product.price.toString() + Constants.TextPicker.CURRENCY
                binding.fragmentProductDetailsTvProductPrice.paintFlags =
                    Paint.STRIKE_THRU_TEXT_FLAG
                binding.fragmentProductDetailsTvSaleProductSalePrice.text =
                    response.product.salePrice.toString() + Constants.TextPicker.CURRENCY
            } else {
                binding.fragmentProductDetailsTvProductPrice.text =
                    response.product.price.toString() + Constants.TextPicker.CURRENCY
                binding.fragmentProductDetailsTvSaleProductSalePrice.visibility = View.GONE
            }
        }

        binding.fragmentPersonalInformationBtnAddCart.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                viewModel.getAddCart(AddToCartRequest(user.uid, args.productId))
            }
        }

        viewModel.addToCartData.observe(this) { response ->
            Toast.makeText(
                requireContext(),
                response.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}