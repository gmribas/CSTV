package com.gmribas.cstv.repository.match

import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.repository.dto.MatchResponseDTO

class MatchRepository(
    private val datasource: IMatchDataSource,
    private val mapper: IMapper<MatchResponse, MatchResponseDTO>
) : IMatchRepository {

    override suspend fun getMatches(
        beginAt: String,
        page: Int
    ): List<MatchResponseDTO> {
        return datasource.getOrderedMatches(beginAt, page).map(mapper::toDTO)
    }

    override suspend fun getMatch(slug: String): MatchResponseDTO {
        return datasource.getMatch(slug).let(mapper::toDTO)
    }
}