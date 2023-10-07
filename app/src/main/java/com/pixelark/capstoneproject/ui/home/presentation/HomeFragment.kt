package com.pixelark.capstoneproject.ui.home.presentation

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelark.capstoneproject.adapter.ProductClickListener
import com.pixelark.capstoneproject.adapter.SaleProductAdapter
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
    override fun onFragmentStarted() {
        binding.fragmentHomeRvSaleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getSaleProducts()
        viewModel.saleProductsData.observe(this) { response ->
            setSaleProductAdapter(response.products)
        }
    }

    private fun setSaleProductAdapter(productList: List<ProductModel>) {
        saleProductAdapter =
            SaleProductAdapter(productList, object : ProductClickListener {
                override fun onClick(selectedProduct: ProductModel) {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                            selectedProduct.id
                        )
                    findNavController().navigate(action)
                }
            })
        binding.fragmentHomeRvSaleRecyclerView.adapter = saleProductAdapter
    }
}