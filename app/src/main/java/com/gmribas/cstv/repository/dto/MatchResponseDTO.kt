package com.gmribas.cstv.repository.dto

data class MatchResponseDTO(
    val slug: String,
    val league: String,
    val serie: String,
    val teamA: TeamDTO?,
    val teamB: TeamDTO?,
    val status: String,
    val beginAt: String,
    val leagueImageUrl: String?,
    val isLive: Boolean,
    val formattedDateLabel: String,
)
