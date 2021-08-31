package com.joule.mynews.remote

import com.joule.mynews.BuildConfig
import okhttp3.Interceptor

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var req = chain.request()
        val url = req.url().newBuilder().addQueryParameter("apiKey", "ecfc6fada0c348038f2da6fe98cd9db6").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}
