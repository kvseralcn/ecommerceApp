package com.pixelark.capstoneproject.core.service

import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import kotlinx.coroutines.flow.Flow

interface StoreApi {
    fun getSaleProducts(): Flow<SaleProductsResponse>
}