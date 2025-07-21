package com.gmribas.cstv.repository

import com.gmribas.cstv.repository.dto.MatchResponseDTO

interface IMatchRepository {

    suspend fun getMatches(beginAt: String, page: Int): List<MatchResponseDTO>

    suspend fun getMatch(slug: String): MatchResponseDTO
}