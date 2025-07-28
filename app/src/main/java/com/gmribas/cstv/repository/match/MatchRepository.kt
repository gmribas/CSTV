package com.gmribas.cstv.repository.match

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.model.MatchOpponentsResponse
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.paging.MatchesPagingSource
import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO

class MatchRepository(
    private val datasource: IMatchDataSource,
    private val mapper: IMapper<MatchResponse, MatchResponseDTO>,
    private val matchOpponentsMapper: IMapper<MatchOpponentsResponse, MatchOpponentsResponseDTO>
) : IMatchRepository {

    override fun getMatchesPagingFlow(): Flow<PagingData<MatchResponseDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MatchesPagingSource(datasource) }
        ).flow.map { pagingData ->
            pagingData.map { matchResponse ->
                mapper.toDTO(matchResponse)
            }
        }
    }
    
    override suspend fun getMatchOpponents(slug: String): MatchOpponentsResponseDTO {
        return matchOpponentsMapper.toDTO(datasource.getMatchOpponents(slug))
    }
}
