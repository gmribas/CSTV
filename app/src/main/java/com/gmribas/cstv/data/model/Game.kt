package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Game(
    val complete: Boolean,
    val id: Long,
    val position: Long,
    val status: String,
    val length: Long?,
    val finished: Boolean,
    @SerializedName("begin_at")
    val beginAt: String?,
    @SerializedName("detailed_stats")
    val detailedStats: Boolean,
    @SerializedName("end_at")
    val endAt: String?,
    val forfeit: Boolean,
    @SerializedName("match_id")
    val matchId: Long,
    @SerializedName("winner_type")
    val winnerType: String,
    val winner: Winner,
)