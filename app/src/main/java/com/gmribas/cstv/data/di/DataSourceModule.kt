package com.gmribas.cstv.data.di

import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.datasource.match.MatchDataSource
import com.gmribas.cstv.data.api.PandascoreApi
import com.gmribas.cstv.data.datasource.team.ITeamDataSource
import com.gmribas.cstv.data.datasource.team.TeamDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Provides
    fun provideIMatchDataSource(pandascoreApi: PandascoreApi): IMatchDataSource = MatchDataSource(pandascoreApi)

    @Provides
    fun provideITeamDataSource(pandascoreApi: PandascoreApi): ITeamDataSource = TeamDataSource(pandascoreApi)
}
