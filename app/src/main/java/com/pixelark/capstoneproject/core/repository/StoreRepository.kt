package com.pixelark.capstoneproject.core.repository

import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getSaleProducts(): Flow<SaleProductsResponse>
    fun getProductDetail(id: Int): Flow<ProductDetailResponse>
}