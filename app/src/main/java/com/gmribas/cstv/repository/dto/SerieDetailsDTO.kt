package com.gmribas.cstv.repository.dto

data class SerieDetailsDTO(
    val id: Long,
    val name: String,
    val fullName: String,
    val year: Long,
    val season: String?,
)
