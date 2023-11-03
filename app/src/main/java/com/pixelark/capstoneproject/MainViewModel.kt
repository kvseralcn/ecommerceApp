package com.pixelark.capstoneproject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.GetCartProductsResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import com.pixelark.capstoneproject.ui.cart.domain.CartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {
    private val _cartProductsData = MutableLiveData<GetCartProductsResponse>()
    val cartProductsData: LiveData<GetCartProductsResponse>
        get() = _cartProductsData

    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            storeRepository.getCartProducts(userId)
                .catch {
                    Log.e(CartViewModel.TAG, it.toString())
                }
                .collect { cartProducts ->
                    Log.d(CartViewModel.TAG, cartProducts.toString())
                    _cartProductsData.postValue(cartProducts)
                }
        }
    }
}