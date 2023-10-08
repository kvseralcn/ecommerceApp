package com.pixelark.capstoneproject.ui.productdetail.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {
    companion object {
        const val TAG = "ProductDetailViewModel"
        const val TAG_CART = "ProductDetailViewModel"
    }

    private val _productDetailData = MutableLiveData<ProductDetailResponse>()
    val productDetailData: LiveData<ProductDetailResponse>
        get() = _productDetailData

    private val _addToCartData = MutableLiveData<AddToCartResponse>()
    val addToCartData: LiveData<AddToCartResponse>
        get() = _addToCartData

    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            storeRepository.getProductDetail(id = id)
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { productDetail ->
                    Log.d(TAG, productDetail.toString())
                    _productDetailData.postValue(productDetail)
                }
        }
    }

    fun getAddCart(request: AddToCartRequest) {
        viewModelScope.launch {
            storeRepository.getAddToCart(request)
                .catch {
                    Log.e(TAG_CART, it.toString())
                }
                .collect { addToCart ->
                    Log.d(TAG_CART, addToCart.toString())
                    _addToCartData.postValue(addToCart)
                }
        }
    }
}