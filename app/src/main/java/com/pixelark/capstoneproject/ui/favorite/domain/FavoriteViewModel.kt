package com.pixelark.capstoneproject.ui.favorite.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pixelark.capstoneproject.core.BaseViewModel
import com.pixelark.capstoneproject.core.data.ProductModel
import com.pixelark.capstoneproject.core.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ProductRepository
) :
    BaseViewModel() {

    private val _favoriteInsertLiveData = MutableLiveData<Boolean>()
    val favoriteInsertLiveData: LiveData<Boolean>
        get() = _favoriteInsertLiveData

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
}
