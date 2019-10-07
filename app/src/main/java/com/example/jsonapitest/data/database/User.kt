package com.example.jsonapitest.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String
)