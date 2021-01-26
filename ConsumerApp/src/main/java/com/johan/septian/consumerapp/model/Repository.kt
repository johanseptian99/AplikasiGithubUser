package com.johan.septian.consumerapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository (
    @field:SerializedName("stargazers_count")
    val stargazersCount: Int,

    @field:SerializedName("language")
    val language: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("updated_at")
    val updatedAt: String
): Parcelable