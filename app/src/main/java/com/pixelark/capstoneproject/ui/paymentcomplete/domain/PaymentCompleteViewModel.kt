package com.pixelark.capstoneproject.ui.paymentcomplete.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.ClearCartRequest
import com.pixelark.capstoneproject.core.data.ClearCartResponse
import com.pixelark.capstoneproject.core.repository.StoreRepository
import com.pixelark.capstoneproject.ui.cart.domain.CartViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentCompleteViewModel @Inject constructor(private val storeRepository: StoreRepository) :
    BaseViewModel() {

    private val _deleteAllProductsData = MutableLiveData<ClearCartResponse>()
    val deleteAllProductsData: LiveData<ClearCartResponse>
        get() = _deleteAllProductsData

    fun deleteAllProducts(request: ClearCartRequest) {
        viewModelScope.launch {
            storeRepository.getClearCart(request)
                .catch {
                    Log.e(CartViewModel.TAG, it.toString())
                }
                .collect { cartProducts ->
                    Log.d(CartViewModel.TAG, cartProducts.toString())
                    _deleteAllProductsData.postValue(cartProducts)
                }
        }
    }
}