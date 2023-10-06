package com.pixelark.capstoneproject.core.service

import com.pixelark.capstoneproject.core.data.SaleProductsResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface RetrofitStoreApi {

    @GET("get_sale_products.php")
    suspend fun getSaleProducts(@Header("store") str: String = "ktechstore"): SaleProductsResponse
}