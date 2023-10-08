package com.pixelark.capstoneproject.core.service

import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreApiImpl @Inject constructor(
    private val retrofitContentApi: RetrofitStoreApi
) :
    StoreApi {

    override fun getSaleProducts(): Flow<SaleProductsResponse> = flow {
        emit(retrofitContentApi.getSaleProducts())
    }

    override fun getProductDetail(id: Int): Flow<ProductDetailResponse> = flow {
        emit(retrofitContentApi.getProductDetail(id = id))
    }

    override fun getAddToCart(request: AddToCartRequest): Flow<AddToCartResponse> = flow {
        emit(retrofitContentApi.getAddToCart(request = request))
    }

    override fun getDeleteFromCart(request: DeleteFromCartRequest): Flow<DeleteFromCartResponse> =
        flow {
            emit(retrofitContentApi.getDeleteFromCart(request = request))
        }
}