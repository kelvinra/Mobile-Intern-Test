package com.example.android.mobiledeveloperinterntest.retrofit

import com.example.android.mobiledeveloperinterntest.models.UsersResponse
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface UserClient {
    @GET("api/users")
    fun getUsers(@Query("page") page: Int): Call<UsersResponse>
}