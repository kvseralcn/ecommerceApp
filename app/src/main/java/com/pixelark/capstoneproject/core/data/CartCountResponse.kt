package com.pixelark.capstoneproject.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CartCountResponse(
    @Json(name = "count") val count: Int,
    @Json(name = "status") val status: Int,
    @Json(name = "message") val message: String
)
