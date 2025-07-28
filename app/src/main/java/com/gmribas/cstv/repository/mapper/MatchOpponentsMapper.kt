package com.gmribas.cstv.repository.mapper

import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.model.MatchOpponentsResponse
import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.PlayerDetailsDTO
import com.gmribas.cstv.repository.dto.TeamDetailsDTO

class MatchOpponentsMapper : IMapper<MatchOpponentsResponse, MatchOpponentsResponseDTO> {

    override fun toDTO(model: MatchOpponentsResponse): MatchOpponentsResponseDTO {
        return MatchOpponentsResponseDTO(
            opponents = model.opponents.map { teamDetails ->
                TeamDetailsDTO(
                    id = teamDetails.id,
                    name = teamDetails.name,
                    slug = teamDetails.slug,
                    acronym = teamDetails.acronym,
                    imageUrl = teamDetails.imageUrl,
                    players = teamDetails.players?.map { player ->
                        PlayerDetailsDTO(
                            name = player.name.orEmpty(),
                            firstName = player.firstName,
                            lastName = player.lastName,
                            imageUrl = player.imageUrl
                        )
                    } ?: emptyList()
                )
            }
        )
    }
}
