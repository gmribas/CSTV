package com.gmribas.cstv.repository.match

import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO

interface IMatchRepository {

    suspend fun getMatches(beginAt: String, page: Int): List<MatchResponseDTO>
    
    suspend fun getMatchDetails(slug: String): MatchDetailsResponseDTO
}
