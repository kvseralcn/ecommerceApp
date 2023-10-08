package com.pixelark.capstoneproject.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddToCartResponse(
    @Json(name = "status") val status: Int,
    @Json(name = "message") val message: String
)