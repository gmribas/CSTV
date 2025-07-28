package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class TeamDetailsResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("acronym")
    val acronym: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("players")
    val players: List<TeamPlayer>?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("modified_at")
    val modifiedAt: String?
)
