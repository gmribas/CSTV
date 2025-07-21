package com.gmribas.cstv.data.di

import com.gmribas.cstv.data.datasource.MatchDataSource
import com.gmribas.cstv.data.api.PandascoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Provides
    fun provideIMatchDataSource(pandascoreApi: PandascoreApi) = MatchDataSource(pandascoreApi)
}
