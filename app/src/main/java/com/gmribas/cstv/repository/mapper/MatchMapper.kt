package com.gmribas.cstv.repository.mapper

import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.repository.dto.MatchResponseDTO

class MatchMapper : IMapper<MatchResponse, MatchResponseDTO> {

    override fun toDTO(model: MatchResponse): MatchResponseDTO {
        return MatchResponseDTO(
            slug = model.slug,
            league = model.league,
            serie = model.serie.name,
//            teamA = ,
//            teamB =
        )
    }
}
