package com.pixelark.capstoneproject.core.service

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
}