package com.pixelark.capstoneproject.ui.productdetail.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.core.data.ProductRepository
import com.pixelark.capstoneproject.core.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val repository: ProductRepository
) :
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

    private val _favoriteInsertLiveData = MutableLiveData<Boolean>()
    val favoriteInsertLiveData: LiveData<Boolean>
        get() = _favoriteInsertLiveData

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

    fun getFavoriteProducts(): List<ProductModel> {
        return repository.getFavoriteProducts()
    }

    fun insertProduct(product: ProductModel) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
            _favoriteInsertLiveData.postValue(false)
        }).launch {
            repository.insertProduct(product)
            _favoriteInsertLiveData.postValue(true)
        }
    }

    fun deleteProduct(productId: Int) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, exception ->
            _favoriteInsertLiveData.postValue(false)
        }).launch {
            repository.deleteProduct(productId)
            _favoriteInsertLiveData.postValue(true)
        }
    }
}