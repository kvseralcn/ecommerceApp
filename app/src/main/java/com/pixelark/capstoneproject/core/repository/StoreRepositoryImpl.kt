package com.pixelark.capstoneproject.core.repository

import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.CategoriesResponse
import com.pixelark.capstoneproject.core.data.ClearCartRequest
import com.pixelark.capstoneproject.core.data.ClearCartResponse
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.GetCartProductsResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.ProductsByCategoryResponse
import com.pixelark.capstoneproject.core.data.ProductsResponse
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import com.pixelark.capstoneproject.core.data.SearchProductsResponse
import com.pixelark.capstoneproject.core.service.StoreApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val contentAPI: StoreApi
) : StoreRepository {
    override fun getProducts(): Flow<ProductsResponse> =
        contentAPI.getProducts()
            .flowOn(Dispatchers.IO)

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

    override fun getClearCart(request: ClearCartRequest): Flow<ClearCartResponse> =
        contentAPI.getClearCart(request)
            .flowOn(Dispatchers.IO)

    override fun getCartProducts(userId: String): Flow<GetCartProductsResponse> =
        contentAPI.getCartProducts(userId)
            .flowOn(Dispatchers.IO)

    override fun getSearchProducts(query: String): Flow<SearchProductsResponse> =
        contentAPI.getSearchProducts(query)
            .flowOn(Dispatchers.IO)

    override fun getCategories(): Flow<CategoriesResponse> =
        contentAPI.getCategories()
            .flowOn(Dispatchers.IO)

    override fun getProductsByCategory(category: String): Flow<ProductsByCategoryResponse> =
        contentAPI.getProductsByCategory(category)
            .flowOn(Dispatchers.IO)
}