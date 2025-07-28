package com.gmribas.cstv.repository.match

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO

interface IMatchRepository {
    fun getMatchesPagingFlow(): Flow<PagingData<MatchResponseDTO>>
    suspend fun getMatchDetails(slug: String): MatchDetailsResponseDTO
}
