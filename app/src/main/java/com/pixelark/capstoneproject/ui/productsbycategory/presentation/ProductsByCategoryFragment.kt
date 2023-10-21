package com.pixelark.capstoneproject.ui.productsbycategory.presentation

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pixelark.capstoneproject.adapter.ProductCategoryClickListener
import com.pixelark.capstoneproject.adapter.ProductsByCategoryAdapter
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FragmentProductsByCategoryBinding
import com.pixelark.capstoneproject.ui.productsbycategory.domain.ProductsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsByCategoryFragment :
    BaseFragment<FragmentProductsByCategoryBinding, ProductsByCategoryViewModel>(
        FragmentProductsByCategoryBinding::inflate, ProductsByCategoryViewModel::class.java
    ) {
    private lateinit var productsByCategoryAdapter: ProductsByCategoryAdapter
    private val argsCategory by navArgs<ProductsByCategoryFragmentArgs>()

    override fun onFragmentStarted() {
        binding.fragmentProductsByCategoryRvProductsByCategoryRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        viewModel.getProductsByCategory(argsCategory.data)
        viewModel.byCategoryData.observe(this) { response ->
            setProductByCategoryAdapter(response.products)
        }
    }

    private fun setProductByCategoryAdapter(productList: List<ProductModel>) {
        productsByCategoryAdapter =
            ProductsByCategoryAdapter(productList,
                object : ProductCategoryClickListener {
                    override fun onCategoryClick(selectedProduct: ProductModel) {
                        val action =
                            ProductsByCategoryFragmentDirections.actionProductsByCategoryFragmentToProductDetailFragment(
                                selectedProduct.id
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentProductsByCategoryRvProductsByCategoryRecyclerView.adapter =
            productsByCategoryAdapter
    }


}