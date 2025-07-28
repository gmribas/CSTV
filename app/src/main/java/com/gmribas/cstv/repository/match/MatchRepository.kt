package com.gmribas.cstv.repository.match

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.datasource.team.ITeamDataSource
import com.gmribas.cstv.data.model.MatchDetailsResponse
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.paging.MatchesPagingSource
import com.gmribas.cstv.repository.dto.MatchDetailsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO

class MatchRepository(
    private val datasource: IMatchDataSource,
    private val teamDataSource: ITeamDataSource,
    private val mapper: IMapper<MatchResponse, MatchResponseDTO>,
    private val matchDetailsMapper: IMapper<MatchDetailsResponse, MatchDetailsResponseDTO>
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
    
    override suspend fun getMatchDetails(slug: String): MatchDetailsResponseDTO {
        try {
            val match = datasource.getMatch(slug)
            
            // Get team details from opponents
            val teamA = match.opponents.getOrNull(0)?.opponent?.let { opponent ->
                try {
                    teamDataSource.getTeamById(opponent.id)
                } catch (e: Exception) {
                    // Log team fetch error but continue
                    println("Failed to fetch team A details: ${e.message}")
                    null
                }
            }
            
            val teamB = match.opponents.getOrNull(1)?.opponent?.let { opponent ->
                try {
                    teamDataSource.getTeamById(opponent.id)
                } catch (e: Exception) {
                    // Log team fetch error but continue
                    println("Failed to fetch team B details: ${e.message}")
                    null
                }
            }
            
            val matchDetailsResponse = MatchDetailsResponse(
                match = match,
                teamA = teamA,
                teamB = teamB
            )
            
            return matchDetailsMapper.toDTO(matchDetailsResponse)
        } catch (e: Exception) {
            println("Failed to fetch match details for slug '$slug': ${e.message}")
            throw e
        }
    }
}
