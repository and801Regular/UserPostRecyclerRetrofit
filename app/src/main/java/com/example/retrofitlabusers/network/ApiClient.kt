package com.example.retrofitlabusers.network

import com.example.retrofitlabusers.dto.PostResponse
import com.example.retrofitlabusers.dto.UserResponse
import com.example.retrofitlabusers.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiClient {
    //@GET("public/v1/users?page=page}")
    //suspend fun getListOfUsers(@Query("page") page: String): Response<UserResponse>
    @GET("public/v1/users?")
    //fun getListOfUsers(@Url url: String):Response<UserResponse>
    suspend fun getListOfUsers(@Query("page") page: Int):Response<UserResponse>
    @GET("public/v1/users/{param}/posts")
    suspend fun getUserPosts(@Path("param") param: String): Response<PostResponse>
}