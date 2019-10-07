package com.example.data

import com.example.domain.Post
import com.example.domain.User

interface RemoteDataSource {
    suspend fun getPosts(): List<Post>
    suspend fun getUsers(): List<User>
}