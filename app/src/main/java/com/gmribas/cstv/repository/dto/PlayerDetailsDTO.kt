package com.gmribas.cstv.repository.dto

data class PlayerDetailsDTO(
    val name: String?,
    val firstName: String?,
    val lastName: String?,
    val imageUrl: String?
) {
    fun getDisplayName(): String {
        return firstName?.let { first ->
            lastName?.let { last -> "$first $last" } ?: first
        } ?: name.orEmpty()
    }
}
