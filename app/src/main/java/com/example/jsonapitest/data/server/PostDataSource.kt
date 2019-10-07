package com.example.jsonapitest.data.server

import com.example.data.RemoteDataSource
import com.example.domain.Post
import com.example.domain.User
import com.example.jsonapitest.data.toDomainPost
import com.example.jsonapitest.data.toDomainUser

class PostDataSource : RemoteDataSource {
    override suspend fun getUsers(): List<User> =
        PostDb.service
            .getAllUsers().await()
            .map { it.toDomainUser() }

    override suspend fun getPosts(): List<Post> =
        PostDb.service
            .getAllPost().await()
            .map { it.toDomainPost() }
}