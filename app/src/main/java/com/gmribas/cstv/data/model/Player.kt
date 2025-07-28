package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Player(
    val id: Long?,
    val name: String?,
    val location: String?,
    val slug: String?,
    @SerializedName("modified_at")
    val modifiedAt: String?,
    val acronym: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
)