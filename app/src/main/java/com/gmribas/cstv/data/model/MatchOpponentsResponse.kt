package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class MatchOpponentsResponse(
    @SerializedName("opponent_type")
    val opponentType: String,
    val opponents: List<TeamDetailsResponse>
)
