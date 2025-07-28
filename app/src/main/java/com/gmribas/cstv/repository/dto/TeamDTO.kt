package com.gmribas.cstv.repository.dto

data class TeamDTO(
    val id: Long?,
    val name: String?,
    val slug: String?,
    val acronym: String?,
    val imageUrl: String?,
    val players: List<PlayerDTO>? = null
)
