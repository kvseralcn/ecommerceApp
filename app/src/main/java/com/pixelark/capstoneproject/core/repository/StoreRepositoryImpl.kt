package com.pixelark.capstoneproject.core.repository

import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import com.pixelark.capstoneproject.core.service.StoreApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val contentAPI: StoreApi
) : StoreRepository {

    override fun getSaleProducts(): Flow<SaleProductsResponse> =
        contentAPI.getSaleProducts()
            .flowOn(Dispatchers.IO)
}