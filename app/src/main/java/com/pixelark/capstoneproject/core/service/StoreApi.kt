package com.pixelark.capstoneproject.core.service

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
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import com.pixelark.capstoneproject.core.data.SearchProductsResponse
import kotlinx.coroutines.flow.Flow

interface StoreApi {
    fun getSaleProducts(): Flow<SaleProductsResponse>
    fun getProductDetail(id: Int): Flow<ProductDetailResponse>
    fun getAddToCart(request: AddToCartRequest): Flow<AddToCartResponse>
    fun getDeleteFromCart(request: DeleteFromCartRequest): Flow<DeleteFromCartResponse>
    fun getClearCart(request: ClearCartRequest): Flow<ClearCartResponse>
    fun getCartProducts(userId: String): Flow<GetCartProductsResponse>
    fun getSearchProducts(query: String): Flow<SearchProductsResponse>
    fun getCategories(): Flow<CategoriesResponse>
    fun getProductsByCategory(category: String): Flow<ProductsByCategoryResponse>
}