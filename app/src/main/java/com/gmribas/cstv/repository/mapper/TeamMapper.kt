package com.gmribas.cstv.repository.mapper

import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.model.Player
import com.gmribas.cstv.repository.dto.TeamDTO

class TeamMapper : IMapper<Player, TeamDTO> {

    override fun toDTO(model: Player): TeamDTO {
        return TeamDTO(
            id = model.id,
            name = model.name,
            slug = model.slug,
            acronym = model.acronym,
            imageUrl = model.imageUrl,
            players = null // Players list will be null for team mapping from match opponents
        )
    }
}
