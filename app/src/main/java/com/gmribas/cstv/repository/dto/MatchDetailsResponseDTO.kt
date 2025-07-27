package com.gmribas.cstv.repository.dto

data class MatchDetailsResponseDTO(
    val slug: String,
    val name: String,
    val status: String,
    val beginAt: String,
    val endAt: String?,
    val isLive: Boolean,
    val formattedDateLabel: String,
    val league: LeagueDetailsDTO,
    val serie: SerieDetailsDTO,
    val tournament: TournamentDetailsDTO,
    val teamA: TeamDetailsDTO?,
    val teamB: TeamDetailsDTO?,
    val numberOfGames: Long,
    val matchType: String,
)
