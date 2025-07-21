package com.gmribas.cstv.repository.dto

data class TeamDTO(
    val name: String,
    val image: String,
    val players: List<PlayerDTO>
)
