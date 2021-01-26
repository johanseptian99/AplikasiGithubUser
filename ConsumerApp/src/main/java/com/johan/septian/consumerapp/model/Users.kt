package com.johan.septian.consumerapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users (
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("html_url")
    val htmlUrl: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("id")
    val id: Int
) : Parcelable