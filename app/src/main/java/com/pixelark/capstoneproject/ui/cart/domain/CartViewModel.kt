package com.pixelark.capstoneproject.ui.cart.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.ClearCartRequest
import com.pixelark.capstoneproject.core.data.ClearCartResponse
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.GetCartProductsResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val auth: FirebaseAuth
) :
    BaseViewModel() {

    companion object {
        const val TAG = "CartViewModel"
    }

    private val _deleteProductsData = MutableLiveData<DeleteFromCartResponse>()
    val deleteProductsData: LiveData<DeleteFromCartResponse>
        get() = _deleteProductsData

    private val _cartProductsData = MutableLiveData<GetCartProductsResponse>()
    val cartProductsData: LiveData<GetCartProductsResponse>
        get() = _cartProductsData

    private val _deleteAllProductsData = MutableLiveData<ClearCartResponse>()
    val deleteAllProductsData: LiveData<ClearCartResponse>
        get() = _deleteAllProductsData

    fun deleteProducts(productId: Int) {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid.orEmpty()
            storeRepository.getDeleteFromCart(DeleteFromCartRequest(productId, userId))
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { cartProducts ->
                    Log.d(TAG, cartProducts.toString())
                    _deleteProductsData.postValue(cartProducts)
                }
        }
    }

    fun deleteAllProducts(request: ClearCartRequest) {
        viewModelScope.launch {
            storeRepository.getClearCart(request)
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { cartProducts ->
                    Log.d(TAG, cartProducts.toString())
                    _deleteAllProductsData.postValue(cartProducts)
                }
        }
    }

    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            storeRepository.getCartProducts(userId)
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { cartProducts ->
                    Log.d(TAG, cartProducts.toString())
                    _cartProductsData.postValue(cartProducts)
                }
        }
    }

}