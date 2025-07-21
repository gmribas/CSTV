package com.gmribas.cstv.core.network.interceptor

import com.gmribas.cstv.BuildConfig
import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor() : Interceptor {

    private val headerKey = "Authorization: Bearer "

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(headerKey, BuildConfig.PANDASCORE_KEY)
            .build()

        return chain.proceed(request)
    }
}
