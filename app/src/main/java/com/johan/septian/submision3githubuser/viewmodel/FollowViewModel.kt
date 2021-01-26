package com.johan.septian.submision3githubuser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johan.septian.submision3githubuser.model.Users
import com.johan.septian.submision3githubuser.retrofit.RetroConfig
import retrofit2.Call
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val listFollowers : MutableLiveData<List<Users>> = MutableLiveData()
    private val listFollowing : MutableLiveData<List<Users>> = MutableLiveData()

    fun loadFollowersUsers(context: Context?, username: String?, page: String){
        RetroConfig.getRetrofit().getFollowersUser(username, page).enqueue(object : retrofit2.Callback<List<Users>>{
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                listFollowers.postValue(response.body())
            }
        })
    }

    fun loadFollowingUsers(context: Context?, username: String?, page: String){
        RetroConfig.getRetrofit().getFollowingUser(username, page).enqueue(object : retrofit2.Callback<List<Users>>{
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                listFollowing.postValue(response.body())
            }
        })
    }

    val getFollowersUsers : LiveData<List<Users>> = listFollowers
    val getFollowingUsers : LiveData<List<Users>> = listFollowing
}