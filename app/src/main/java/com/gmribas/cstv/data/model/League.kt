package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class League(
    val id: Long,
    val name: String,
    val url: String?,
    val slug: String,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("image_url")
    val imageUrl: Any?,
)