package com.pixelark.capstoneproject.ui.home.presentation

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelark.capstoneproject.adapter.CategoryAdapter
import com.pixelark.capstoneproject.adapter.CategoryClickListener
import com.pixelark.capstoneproject.adapter.SaleProductAdapter
import com.pixelark.capstoneproject.adapter.SaleProductClickListener
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FragmentHomeBinding
import com.pixelark.capstoneproject.ui.home.domain.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate, HomeViewModel::class.java
) {
    private lateinit var saleProductAdapter: SaleProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onFragmentStarted() {
        binding.fragmentHomeRvSaleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.fragmentHomeRvCategoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getSaleProducts()
        viewModel.saleProductsData.observe(this) { response ->
            setSaleProductAdapter(response.products)
        }

        viewModel.getCategories()
        viewModel.categoriesData.observe(this) { response ->
            setCategoryAdapter(response.categories)
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