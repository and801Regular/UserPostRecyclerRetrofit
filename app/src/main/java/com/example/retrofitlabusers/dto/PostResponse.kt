package com.example.retrofitlabusers.dto

import com.example.retrofitlabusers.model.Meta
import com.example.retrofitlabusers.model.Post
import com.example.retrofitlabusers.model.User

class PostResponse {
    var meta: Meta?=null
    var data: ArrayList<Post>?=null
}