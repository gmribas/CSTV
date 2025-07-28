package com.gmribas.cstv.repository.dto

data class MatchOpponentsResponseDTO(
    val opponentType: String,
    val opponents: List<TeamDetailsDTO>
)
