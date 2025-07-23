package com.gmribas.cstv.repository.dto

import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.TeamDetailsResponse

data class MatchMapperDTO(
    val match: MatchResponse,
    val teamA: TeamDetailsResponse?,
    val teamB: TeamDetailsResponse?,
)
