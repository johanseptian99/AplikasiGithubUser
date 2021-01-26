package com.johan.septian.consumerapp.retrofit

import com.johan.septian.consumerapp.BuildConfig
import com.johan.septian.consumerapp.model.DetailUser
import com.johan.septian.consumerapp.model.Repository
import com.johan.septian.consumerapp.model.SearchUsers
import com.johan.septian.consumerapp.model.Users
import retrofit2.Call
import retrofit2.http.*

const val TOKEN : String = BuildConfig.GITHUB_TOKEN

interface ApiGithub {
    @GET("/users")
    @Headers("Authorization: token $TOKEN")
    fun getUserData(
    ) : Call<List<Users>>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    fun getSearchData(
        @Query("q") query: String?
    ) : Call<SearchUsers>

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    fun getFollowersUser(
        @Path("username") username : String?,
        @Query("page") page: String
    ) : Call<List<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    fun getFollowingUser(
        @Path("username") username : String?,
        @Query("page") page: String
    ) : Call<List<Users>>

    @GET("users/{username}/repos")
    @Headers("Authorization: token $TOKEN")
    fun getRepositoryData(
        @Path("username") username : String?,
    ) : Call<List<Repository>>

}