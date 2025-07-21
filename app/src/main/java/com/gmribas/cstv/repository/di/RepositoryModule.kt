package com.gmribas.cstv.repository.di

import com.gmribas.cstv.core.IMapper
import com.gmribas.cstv.data.datasource.IMatchDataSource
import com.gmribas.cstv.data.model.MatchResponse
import com.gmribas.cstv.repository.IMatchRepository
import com.gmribas.cstv.repository.MatchRepository
import com.gmribas.cstv.repository.dto.MatchResponseDTO
import com.gmribas.cstv.repository.mapper.MatchMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideMatchMapper(): IMapper<MatchResponse, MatchResponseDTO> = MatchMapper()

    @Provides
    fun provideMatchRepository(
        datasource: IMatchDataSource,
        mapper: IMapper<MatchResponse, MatchResponseDTO>
    ): IMatchRepository {
        return MatchRepository(datasource, mapper)
    }
}
