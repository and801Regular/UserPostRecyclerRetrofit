package com.example.retrofitlabusers.network

import com.example.retrofitlabusers.dto.AuthResponse
import com.example.retrofitlabusers.model.authRequest
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthClient {
    @POST("/api/Account/authenticate")
    suspend fun authenticate(@Body requestBody: authRequest):retrofit2.Response<AuthResponse>

}