package com.gmribas.cstv.repository.match

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO

interface IMatchRepository {
    fun getMatchesPagingFlow(): Flow<PagingData<MatchResponseDTO>>
    suspend fun getMatchOpponents(slug: String): MatchOpponentsResponseDTO
}
