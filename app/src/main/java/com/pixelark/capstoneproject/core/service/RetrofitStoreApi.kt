package com.pixelark.capstoneproject.core.service

import com.pixelark.capstoneproject.core.data.AddToCartRequest
import com.pixelark.capstoneproject.core.data.AddToCartResponse
import com.pixelark.capstoneproject.core.data.DeleteFromCartRequest
import com.pixelark.capstoneproject.core.data.DeleteFromCartResponse
import com.pixelark.capstoneproject.core.data.GetCartProductsResponse
import com.pixelark.capstoneproject.core.data.ProductDetailResponse
import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import com.pixelark.capstoneproject.core.data.SearchProductsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitStoreApi {

    @GET("get_sale_products.php")
    suspend fun getSaleProducts(@Header("store") str: String = "ktechstore"): SaleProductsResponse

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Header("store") str: String = "ktechstore",
        @Query("id") id: Int
    ): ProductDetailResponse

    @POST("add_to_cart.php")
    suspend fun getAddToCart(
        @Header("store") str: String = "ktechstore",
        @Body request: AddToCartRequest
    ): AddToCartResponse

    @GET("get_cart_products.php")
    suspend fun getCartProducts(
        @Header("store") str: String = "ktechstore",
        @Query("userId") userId: String
    ): GetCartProductsResponse

    @POST("delete_from_cart.php")
    suspend fun getDeleteFromCart(
        @Header("store") str: String = "ktechstore",
        @Body request: DeleteFromCartRequest
    ): DeleteFromCartResponse

    @GET("search_product.php")
    suspend fun getSearchProducts(
        @Header("store") str: String = "ktechstore",
        @Query("query") query: String
    ): SearchProductsResponse

}