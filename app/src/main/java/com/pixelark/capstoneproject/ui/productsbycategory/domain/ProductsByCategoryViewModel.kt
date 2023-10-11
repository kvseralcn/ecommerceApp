package com.pixelark.capstoneproject.ui.productsbycategory.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.ProductsByCategoryResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsByCategoryViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {
    companion object {
        const val TAG = "ProductsByCategoryViewModel"
    }

    private val _byCategoryData = MutableLiveData<ProductsByCategoryResponse>()
    val byCategoryData: LiveData<ProductsByCategoryResponse>
        get() = _byCategoryData

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            storeRepository.getProductsByCategory(category = category)
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { products ->
                    Log.d(TAG, products.toString())
                    _byCategoryData.postValue(products)
                }
        }
    }
}