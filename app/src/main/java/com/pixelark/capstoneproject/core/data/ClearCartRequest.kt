package com.pixelark.capstoneproject.core.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClearCartRequest(
    @Json(name = "userId") val userId: String
)