package com.example.domain

data class Comment(
    val commentId: Int,
    val postId: Int,
    val body: String
)