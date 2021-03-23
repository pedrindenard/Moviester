package com.app.moviester.retrofit

import com.app.moviester.retrofit.ApiConfig.API_KEY
import com.app.moviester.retrofit.ApiConfig.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit, OkHttpClient e Json

class AppRetrofit {
    private val client = OkHttpClient
        .Builder()
        .addInterceptor(Intercepto)
        .build()

    private val retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val movieService: Api by lazy {
        retrofit.create(Api::class.java)
    }
}

object Intercepto : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = originalRequest
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}