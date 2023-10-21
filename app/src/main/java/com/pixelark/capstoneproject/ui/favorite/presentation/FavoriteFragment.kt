package com.pixelark.capstoneproject.ui.favorite.presentation

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.pixelark.capstoneproject.adapter.FavoriteProductAdapter
import com.pixelark.capstoneproject.adapter.FavoriteProductClickListener
import com.pixelark.capstoneproject.core.BaseFragment
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.databinding.FragmentFavoriteBinding
import com.pixelark.capstoneproject.ui.favorite.domain.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate, FavoriteViewModel::class.java
) {
    private lateinit var favoriteProductAdapter: FavoriteProductAdapter
    override fun onFragmentStarted() {
        binding.fragmentHomeRvFavoriteProductRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        CoroutineScope(Dispatchers.IO).launch {
            val favoriteProducts = viewModel.getFavoriteProducts()
            withContext(Dispatchers.Main) {
                setFavoriteProductAdapter(favoriteProducts)
            }
        }
    }

    private fun setFavoriteProductAdapter(productList: List<ProductModel>) {
        favoriteProductAdapter =
            FavoriteProductAdapter(productList,
                object : FavoriteProductClickListener {
                    override fun onClick(selectedProduct: ProductModel) {
                        val action =
                            FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailFragment(
                                selectedProduct.id
                            )
                        findNavController().navigate(action)
                    }
                }
            )
        binding.fragmentHomeRvFavoriteProductRecyclerView.adapter = favoriteProductAdapter
    }
}