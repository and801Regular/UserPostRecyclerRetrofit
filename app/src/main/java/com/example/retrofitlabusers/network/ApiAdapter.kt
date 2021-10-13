package com.example.retrofitlabusers.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiAdapter{
    val apiClient:ApiClient = Retrofit.Builder()
        .baseUrl("https://gorest.co.in")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)
}