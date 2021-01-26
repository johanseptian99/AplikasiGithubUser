package com.johan.septian.submision3githubuser.retrofit

import com.johan.septian.submision3githubuser.retrofit.ApiGithub
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroConfig {
    companion object Factory{
        fun getRetrofit() : ApiGithub {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiGithub::class.java)

        }
    }
}