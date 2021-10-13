package com.example.retrofitlabusers.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAuthAdapter {
    val apiAuthClient:AuthClient = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5000")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthClient::class.java)
}