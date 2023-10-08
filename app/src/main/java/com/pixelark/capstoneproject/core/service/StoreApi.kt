package com.pixelark.capstoneproject.core.service

import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import kotlinx.coroutines.flow.Flow

interface StoreApi {
    fun getSaleProducts(): Flow<SaleProductsResponse>
    fun getProductDetail(id: Int): Flow<ProductDetailResponse>
    fun getAddToCart(request: AddToCartRequest): Flow<AddToCartResponse>
    fun getDeleteFromCart(request: DeleteFromCartRequest): Flow<DeleteFromCartResponse>
}