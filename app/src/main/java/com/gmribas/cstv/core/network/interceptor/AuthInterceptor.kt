package com.gmribas.cstv.core.network.interceptor

import com.gmribas.cstv.BuildConfig
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor() : Interceptor {

    private val headerKey = "Authorization"
    private val headerValue = "Bearer ${BuildConfig.PANDASCORE_KEY}"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(headerKey, headerValue)
            .build()

        return chain.proceed(request)
    }
}
