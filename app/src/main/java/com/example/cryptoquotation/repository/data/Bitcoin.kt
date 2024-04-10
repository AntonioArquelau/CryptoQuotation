package com.example.cryptoquotation.repository.data

import com.google.gson.annotations.SerializedName

data class Bitcoin(
    @SerializedName("asset_id_base")
    val assetIdBase: String? = null,
    @SerializedName("asset_id_quote")
    val assetIdQuote: String? = null,
    @SerializedName("rate")
    val rate: String? = null,
    @SerializedName("time")
    val time: String? = null
)
