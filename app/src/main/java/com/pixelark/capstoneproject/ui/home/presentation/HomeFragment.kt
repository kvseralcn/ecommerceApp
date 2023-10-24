package com.pixelark.capstoneproject.ui.home.presentation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.pixelark.capstoneproject.R
import com.pixelark.capstoneproject.adapter.CategoryAdapter
import com.pixelark.capstoneproject.adapter.CategoryClickListener
import com.pixelark.capstoneproject.adapter.FavoriteProductClickListener
import com.pixelark.capstoneproject.adapter.ProductAdapter
import com.pixelark.capstoneproject.adapter.SaleProductAdapter
import com.pixelark.capstoneproject.adapter.SaleProductClickListener
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.CustomCampaignDialogBinding
import com.pixelark.capstoneproject.databinding.FragmentHomeBinding
import com.pixelark.capstoneproject.ui.home.domain.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    private lateinit var saleProductAdapter: SaleProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var productAdapter: ProductAdapter
    override fun onFragmentStarted() {
        binding.fragmentHomeRvSaleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeRvCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeRvProductRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.getSaleProducts()
        viewModel.saleProductsData.observe(this) { response ->
            setSaleProductAdapter(response.products)
        }

        viewModel.getCategories()
        viewModel.categoriesData.observe(this) { response ->
            setCategoryAdapter(response.categories)
        }

        viewModel.getProducts()
        viewModel.productsData.observe(this) { response ->
            setProductAdapter(response.products)
        }

        showCampaign()
    }

    private fun showCampaign() {
        val dialogBinding = CustomCampaignDialogBinding.inflate(layoutInflater)
        val myDialog = Dialog(requireContext())
        myDialog.setContentView(dialogBinding.root)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val window = myDialog.window
        val layoutParams = window?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
        myDialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        myDialog.window!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.bottom_sheet_background_tint
            )
        )
        myDialog.window!!.setGravity(Gravity.CENTER)

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val textFromRemoteConfig = firebaseRemoteConfig.getString("campaign")
                val imageFromRemoteConfig = firebaseRemoteConfig.getString("image")
                //dialogBinding.customCampaignDialogTvInfo.text = textFromRemoteConfig
                Glide.with(this)
                    .load(imageFromRemoteConfig)
                    .into(dialogBinding.customCampaignDialogIvCampaign)
            }
        }

        myDialog.show()

        dialogBinding.customCampaignDialogBtnOk.setOnClickListener {
            myDialog.dismiss()
        }
    }

    private fun setSaleProductAdapter(productList: List<ProductModel>) {
        saleProductAdapter =
            SaleProductAdapter(productList,
                object : SaleProductClickListener {
                    override fun onClick(selectedProduct: ProductModel) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                                selectedProduct.id
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvSaleRecyclerView.adapter = saleProductAdapter
    }

    private fun setProductAdapter(productList: List<ProductModel>) {
        productAdapter =
            ProductAdapter(productList,
                object : FavoriteProductClickListener {
                    override fun onClick(selectedProduct: ProductModel) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                                selectedProduct.id
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvProductRecyclerView.adapter = productAdapter
    }

    private fun setCategoryAdapter(productList: List<String>) {
        categoryAdapter =
            CategoryAdapter(productList,
                object : CategoryClickListener {
                    override fun onCategoryClick(selectedProduct: String) {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToProductsByCategoryFragment(
                                selectedProduct
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvCategoryRecyclerView.adapter = categoryAdapter
    }
}