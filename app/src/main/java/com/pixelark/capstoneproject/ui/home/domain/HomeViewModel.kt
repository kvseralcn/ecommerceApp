package com.pixelark.capstoneproject.ui.home.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    private val _saleProductsData = MutableLiveData<SaleProductsResponse>()
    val saleProductsData: LiveData<SaleProductsResponse>
        get() = _saleProductsData

    fun getSaleProducts() {
        viewModelScope.launch {
            storeRepository.getSaleProducts()
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { products ->
                    Log.d(TAG, products.toString())
                    _saleProductsData.postValue(products)
                }
        }
    }
}