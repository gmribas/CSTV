package com.gmribas.cstv.data.model

import com.google.gson.annotations.SerializedName

data class Serie(
    val id: Long,
    val name: String,
    val year: Long,
    @SerializedName("begin_at")
    val beginAt: String,
    @SerializedName("end_at")
    val endAt: String,
    @SerializedName("winner_id")
    val winnerId: Any?,
    @SerializedName("winner_type")
    val winnerType: String,
    val slug: String,
    @SerializedName("modified_at")
    val modifiedAt: String,
    @SerializedName("league_id")
    val leagueId: Long,
    val season: String?,
    @SerializedName("full_name")
    val fullName: String,
)
