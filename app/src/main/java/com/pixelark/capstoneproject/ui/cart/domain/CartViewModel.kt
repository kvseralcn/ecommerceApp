package com.pixelark.capstoneproject.ui.cart.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.GetCartProductsResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {

    companion object {
        const val TAG = "CartViewModel"
    }

    private val _cartData = MutableLiveData<DeleteFromCartResponse>()
    val cartData: LiveData<DeleteFromCartResponse>
        get() = _cartData

    private val _cartProductsData = MutableLiveData<GetCartProductsResponse>()
    val cartProductsData: LiveData<GetCartProductsResponse>
        get() = _cartProductsData

    fun deleteProducts(request: DeleteFromCartRequest) {
        viewModelScope.launch {
            storeRepository.getDeleteFromCart(request)
                .catch {
                    Log.e(TAG, it.toString())
                }
                .collect { cartProducts ->
                    Log.d(TAG, cartProducts.toString())
                    _cartData.postValue(cartProducts)
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