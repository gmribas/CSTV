package com.gmribas.cstv.repository.mapper

import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.model.Team
import com.gmribas.cstv.repository.dto.TeamDTO

class TeamToTeamDTOMapper : IMapper<Team, TeamDTO> {

    override fun toDTO(model: Team): TeamDTO {
        return TeamDTO(
            id = model.id,
            name = model.name,
            slug = model.slug,
            acronym = model.acronym,
            imageUrl = model.imageUrl,
            players = null
        )
    }
}
