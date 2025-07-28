package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    val id: Long?,
    val name: String?,
    val slug: String?,
    val acronym: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
)
