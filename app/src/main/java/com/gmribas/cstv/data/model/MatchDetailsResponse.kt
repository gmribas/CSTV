package com.gmribas.cstv.data.model

data class MatchDetailsResponse(
    val match: MatchResponse,
    val teamA: TeamDetailsResponse?,
    val teamB: TeamDetailsResponse?,
)
