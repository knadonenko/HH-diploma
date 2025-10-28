package ru.practicum.android.diploma.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.BuildConfig

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
            .build()
        return chain.proceed(request)
    }
}
