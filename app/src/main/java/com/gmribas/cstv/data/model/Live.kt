package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Live(
    val supported: Boolean,
    val url: String?,
    @SerializedName("opens_at")
    val opensAt: String?,
)