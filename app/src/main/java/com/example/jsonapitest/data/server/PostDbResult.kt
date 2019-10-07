package com.example.jsonapitest.data.server

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val postId: Int,
    @SerializedName("id") val userId: Int,
    val title: String,
    val body: String,
    var wasRead: Boolean,
    val favorite: Boolean
) : Parcelable

@Parcelize
data class User(
    val userId: Int,
    val name: String,
    val email: String,
    val phone: String,
    val website: String
) : Parcelable