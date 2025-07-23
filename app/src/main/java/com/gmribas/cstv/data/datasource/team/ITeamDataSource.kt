package com.gmribas.cstv.data.datasource.team

import com.gmribas.cstv.data.model.TeamDetailsResponse

interface ITeamDataSource {

    suspend fun getTeamById(teamIdOrSlug: Long): TeamDetailsResponse
}
