package com.pixelark.capstoneproject.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class AddToCartRequest(
    @Json(name = "userId") val userId: String,
    @Json(name = "productId") val productId: Int
) : Serializable