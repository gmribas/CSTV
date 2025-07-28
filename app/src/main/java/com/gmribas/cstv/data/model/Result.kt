package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("team_id")
    val teamId: Long?,
    val score: Long?,
)