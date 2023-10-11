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
import com.pixelark.capstoneproject.core.data.ProductsResponse
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import com.pixelark.capstoneproject.core.data.SearchProductsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StoreApiImpl @Inject constructor(
    private val retrofitContentApi: RetrofitStoreApi
) :
    StoreApi {
    override fun getProducts(): Flow<ProductsResponse> = flow {
        emit(retrofitContentApi.getProducts())
    }

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

    override fun getClearCart(request: ClearCartRequest): Flow<ClearCartResponse> =
        flow {
            emit(retrofitContentApi.getClearCart(request = request))
        }

    override fun getCartProducts(userId: String): Flow<GetCartProductsResponse> =
        flow {
            emit(retrofitContentApi.getCartProducts(userId = userId))
        }

    override fun getSearchProducts(query: String): Flow<SearchProductsResponse> =
        flow {
            emit(retrofitContentApi.getSearchProducts(query = query))
        }

    override fun getCategories(): Flow<CategoriesResponse> = flow {
        emit(retrofitContentApi.getCategories())
    }

    override fun getProductsByCategory(category: String): Flow<ProductsByCategoryResponse> =
        flow {
            emit(retrofitContentApi.getProductsByCategory(category = category))
        }
}