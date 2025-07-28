package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class VideogameTitle(
    val id: Long?,
    val name: String?,
    val slug: String?,
    @SerializedName("videogame_id")
    val videogameId: Long?,
)