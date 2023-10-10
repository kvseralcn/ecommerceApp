package com.pixelark.capstoneproject.ui.search.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.SearchProductsResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import com.pixelark.capstoneproject.ui.productdetail.domain.ProductDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {

    companion object {
        const val TAG = "SearchViewModel"
    }

    private val _searchProductData = MutableLiveData<SearchProductsResponse>()
    val searchProductData: LiveData<SearchProductsResponse>
        get() = _searchProductData

    fun getSearchProduct(query: String) {
        viewModelScope.launch {
            storeRepository.getSearchProducts(query = query)
                .catch {
                    Log.e(ProductDetailViewModel.TAG, it.toString())
                }
                .collect { product ->
                    Log.d(ProductDetailViewModel.TAG, product.toString())
                    _searchProductData.postValue(product)
                }
        }
    }
}