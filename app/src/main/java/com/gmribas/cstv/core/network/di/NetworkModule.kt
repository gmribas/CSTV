package com.gmribas.cstv.core.network.di

import com.gmribas.cstv.core.network.adapter.NetworkCallAdapterFactory
import com.gmribas.cstv.core.network.interceptor.AuthInterceptor
import com.gmribas.cstv.data.api.PandascoreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    private const val BASE_URL = "https://api.pandascore.co/csgo/matches"

    @Provides
    fun providePandascoreApi(retrofit: Retrofit) = retrofit.create(PandascoreApi::class.java)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory,
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(callAdapterFactory)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(HttpLoggingInterceptor())
        addInterceptor(authInterceptor)
    }.build()

    @Provides
    fun provideNetworkCallAdapterFactory(): CallAdapter.Factory = NetworkCallAdapterFactory()
}
