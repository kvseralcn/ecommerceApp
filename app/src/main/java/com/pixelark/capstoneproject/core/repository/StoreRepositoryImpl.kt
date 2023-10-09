package com.pixelark.capstoneproject.core.repository

import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.GetCartProductsResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
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

    override fun getProductDetail(id: Int): Flow<ProductDetailResponse> =
        contentAPI.getProductDetail(id)
            .flowOn(Dispatchers.IO)

    override fun getAddToCart(request: AddToCartRequest): Flow<AddToCartResponse> =
        contentAPI.getAddToCart(request)
            .flowOn(Dispatchers.IO)

    override fun getDeleteFromCart(request: DeleteFromCartRequest): Flow<DeleteFromCartResponse> =
        contentAPI.getDeleteFromCart(request)
            .flowOn(Dispatchers.IO)

    override fun getCartProducts(userId: String): Flow<GetCartProductsResponse> =
        contentAPI.getCartProducts(userId)
            .flowOn(Dispatchers.IO)
}