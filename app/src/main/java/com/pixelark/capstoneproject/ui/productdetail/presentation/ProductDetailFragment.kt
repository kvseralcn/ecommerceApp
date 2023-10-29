package com.pixelark.capstoneproject.ui.productdetail.presentation

import android.animation.Animator
import android.graphics.Color
import android.graphics.Paint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FragmentProductDetailsBinding
import com.pixelark.capstoneproject.ui.productdetail.domain.ProductDetailViewModel
import com.pixelark.capstoneproject.util.Constants
import com.pixelark.capstoneproject.util.getPriceWithCurrency
import com.pixelark.capstoneproject.util.getSalePriceWithCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailsBinding, ProductDetailViewModel>(
    FragmentProductDetailsBinding::inflate, ProductDetailViewModel::class.java
) {

    private var favoriteProducts: List<ProductModel> = emptyList()
    private var isProductInFavorites: Boolean = false
    private val args by navArgs<ProductDetailFragmentArgs>()

    override fun onFragmentStarted() {
        addOnBackPressedActions()
        CoroutineScope(Dispatchers.IO).launch {
            favoriteProducts = viewModel.getFavoriteProducts()
        }
        viewModel.getProductDetail(args.productId)
        viewModel.productDetailData.observe(this) { response ->
            initProductDetail(response)
        }

        viewModel.favoriteInsertLiveData.observe(viewLifecycleOwner) { isSuccess ->
            val message =
                if (isSuccess) getString(R.string.add_favorite_success)
                else getString(R.string.add_favorite_error)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            CoroutineScope(Dispatchers.IO).launch {
                favoriteProducts = viewModel.getFavoriteProducts()
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

    private fun addOnBackPressedActions() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateUp()
            }
        })
        binding.fragmentProductDetailsBtnBack.setOnClickListener {
            navigateUp()
        }
    }

    private fun initProductDetail(response: ProductDetailResponse) {
        //Set product info
        Glide.with(this)
            .load(response.product.imageOne)
            .into(binding.fragmentProductDetailsIvProductImage)
        binding.fragmentProductDetailsTvSaleProductTitle.text =
            response.product.title
        binding.fragmentProductDetailsTvSaleProductDescription.text =
            response.product.description
        binding.fragmentProductDetailsRbBar.rating =
            response.product.rate
        binding.fragmentProductDetailsTvSaleProductRate.text =
            response.product.rate.toString()

        //Set stock count
        when (response.product.count) {
            in 2..5 -> {
                binding.fragmentProductDetailsTvSaleProductCount.text =
                    getString(R.string.stock_almost_empty)
            }

            1 -> {
                binding.fragmentProductDetailsTvSaleProductCount.text =
                    getString(R.string.last_n_product_left, 1)
            }

            0 -> {
                binding.fragmentProductDetailsTvSaleProductCount.text =
                    getString(R.string.stock_not_available)
                binding.fragmentPersonalInformationBtnAddCart.alpha = 0.35f
                binding.fragmentPersonalInformationBtnAddCart.isClickable = false
            }

            else -> {
                binding.fragmentProductDetailsTvSaleProductCount.text =
                    getString(R.string.stock_available)
            }
        }

        //Set price
        binding.fragmentProductDetailsTvProductPrice.text =
            response.product.getPriceWithCurrency(Constants.Currency.TL)
        binding.fragmentProductDetailsTvProductPrice.textSize = 20F
        if (response.product.saleState == true) {
            binding.fragmentProductDetailsTvSaleProductSalePrice.isVisible = true
            binding.fragmentProductDetailsTvProductPrice.paintFlags =
                Paint.STRIKE_THRU_TEXT_FLAG
            binding.fragmentProductDetailsTvProductPrice.setTextColor(Color.RED)
            binding.fragmentProductDetailsTvSaleProductSalePrice.text =
                response.product.getSalePriceWithCurrency(Constants.Currency.TL)
        }

        //Favorite
        isProductInFavorites = favoriteProducts.any { it.id == response.product.id }
        if (isProductInFavorites) {
            binding.favoriteAnimationView.progress = 1f
        }

        binding.favoriteAnimationView.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                binding.favoriteAnimationView.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.favoriteAnimationView.isEnabled = true
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        binding.favoriteAnimationView.setOnClickListener {
            isProductInFavorites = favoriteProducts.any { it.id == response.product.id }
            if (isProductInFavorites) {
                binding.favoriteAnimationView.progress = 0f
                viewModel.deleteProduct(response.product.id)
                Toast.makeText(
                    requireContext(),
                    "Bu ürün favori listesinden çıkarıldı",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (!binding.favoriteAnimationView.isAnimating) {
                    binding.favoriteAnimationView.playAnimation()
                }
                viewModel.insertProduct(response.product)
            }
        }
    }
}