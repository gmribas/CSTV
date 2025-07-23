package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class TeamPlayer(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("role")
    val role: String?,
)