package com.pixelark.capstoneproject.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductsByCategoryResponse(
    @Json(name = "products") val products: List<ProductModel>,
    @Json(name = "status") val status: Int,
    @Json(name = "message") val message: String
)