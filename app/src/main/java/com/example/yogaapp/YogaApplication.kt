package com.example.yogaapp

import android.app.Application
import com.example.yogaapp.api.YogaApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class YogaApplication : Application() {
    val apiService: YogaApiService by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // Adjust the connect timeout
            .readTimeout(30, TimeUnit.SECONDS)    // Adjust the read timeout
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://yoga-api-nzy4.onrender.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(YogaApiService::class.java)
    }

    val repository: Repository by lazy {
        Repository(apiService)
    }

    override fun onCreate() {
        super.onCreate()
    }
}