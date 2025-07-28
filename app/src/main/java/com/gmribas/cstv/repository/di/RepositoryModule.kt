package com.gmribas.cstv.repository.di

import android.content.Context
import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.datasource.match.IMatchDataSource
import com.gmribas.cstv.data.model.MatchOpponentsResponse
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.data.model.Player
import com.gmribas.cstv.data.model.Team
import com.gmribas.cstv.repository.dto.MatchOpponentsResponseDTO
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.dto.TeamDTO
import com.gmribas.cstv.repository.mapper.MatchMapper
import com.gmribas.cstv.repository.mapper.MatchOpponentsMapper
import com.gmribas.cstv.repository.mapper.TeamMapper
import com.gmribas.cstv.repository.mapper.TeamToTeamDTOMapper
import com.gmribas.cstv.repository.match.IMatchRepository
import com.gmribas.cstv.repository.match.MatchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlin.jvm.JvmSuppressWildcards

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideTeamMapper(): IMapper<Player, TeamDTO> = TeamMapper()
    
    @Provides
    fun provideTeamToTeamDTOMapper(): IMapper<Team, TeamDTO> = TeamToTeamDTOMapper()

    @Provides
    fun provideMatchMapper(
        @ApplicationContext context: Context,
        teamMapper: @JvmSuppressWildcards IMapper<Team, TeamDTO>
    ): IMapper<MatchResponse, MatchResponseDTO> = MatchMapper(context, teamMapper)

    @Provides
    fun provideMatchOpponentsMapper(): IMapper<MatchOpponentsResponse, MatchOpponentsResponseDTO> = MatchOpponentsMapper()

    @Provides
    fun provideMatchRepository(
        datasource: IMatchDataSource,
        mapper: @JvmSuppressWildcards IMapper<MatchResponse, MatchResponseDTO>,
        matchOpponentsMapper: @JvmSuppressWildcards IMapper<MatchOpponentsResponse, MatchOpponentsResponseDTO>
    ): IMatchRepository {
        return MatchRepository(datasource, mapper, matchOpponentsMapper)
    }
}
