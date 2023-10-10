package com.pixelark.capstoneproject.ui.search.presentation

import android.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelark.capstoneproject.adapter.SearchProductAdapter
import com.pixelark.capstoneproject.adapter.SearchProductClickListener
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FragmentSearchBinding
import com.pixelark.capstoneproject.ui.search.domain.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate, SearchViewModel::class.java
) {
    private lateinit var searchProductAdapter: SearchProductAdapter

    override fun onFragmentStarted() {
        binding.fragmentSearchRvSaleRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        var searchJob: Job? = null

        binding.fragmentSearchIdSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                if (newText != null && newText.length >= 3) {
                    searchJob = lifecycleScope.launch {
                        delay(300)
                        viewModel.getSearchProduct(newText.toString())
                    }
                }
                return true
            }
        })

        viewModel.searchProductData.observe(this) { response ->
            setSearchProductAdapter(response.products)
        }
    }

    private fun setSearchProductAdapter(searchProductList: MutableList<ProductModel>) {
        searchProductAdapter =
            SearchProductAdapter(searchProductList, object : SearchProductClickListener {
                override fun onSearchClick(selectedProduct: ProductModel) {
                    val action =
                        SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(
                            selectedProduct.id
                        )
                    findNavController().navigate(action)
                }
            })
        binding.fragmentSearchRvSaleRecyclerView.adapter = searchProductAdapter
    }
}