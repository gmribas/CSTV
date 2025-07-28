package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class StreamsList(
    val main: Boolean?,
    val language: String?,
    @SerializedName("embed_url")
    val embedUrl: String?,
    val official: Boolean?,
    @SerializedName("raw_url")
    val rawUrl: String?,
)