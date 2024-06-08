package com.example.android.mobiledeveloperinterntest.retrofit


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiService {
    private const val BASE_URL = "https://reqres.in/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: UserClient by lazy {
        retrofit.create(UserClient::class.java)
    }
}
