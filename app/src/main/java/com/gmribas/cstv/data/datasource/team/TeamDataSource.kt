package com.gmribas.cstv.data.datasource.team

import com.gmribas.cstv.data.api.PandascoreApi

class TeamDataSource(
    private val pandascoreApi: PandascoreApi
) : ITeamDataSource {

    override suspend fun getTeamById(teamIdOrSlug: Long) = pandascoreApi.getTeamById(teamIdOrSlug)
}
