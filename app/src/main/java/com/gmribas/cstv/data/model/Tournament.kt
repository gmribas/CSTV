package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Tournament(
    val id: Long,
    val name: String,
    val type: String,
    val country: String?,
    @SerializedName("begin_at")
    val beginAt: String,
    @SerializedName("detailed_stats")
    val detailedStats: Boolean,
    @SerializedName("end_at")
    val endAt: String,
    @SerializedName("winner_id")
    val winnerId: Any?,
    @SerializedName("winner_type")
    val winnerType: String,
    val slug: String,
    @SerializedName("serie_id")
    val serieId: Long,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("league_id")
    val leagueId: Long,
    @SerializedName("has_bracket")
    val hasBracket: Boolean,
    val prizepool: String?,
    val region: String,
    val tier: String,
    @SerializedName("live_supported")
    val liveSupported: Boolean,
)
