package com.johan.septian.consumerapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johan.septian.consumerapp.model.Users
import com.johan.septian.consumerapp.retrofit.RetroConfig
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val listUsers : MutableLiveData<List<Users>> = MutableLiveData()

    fun loadHomeUsers(context: Context?){
        RetroConfig.getRetrofit().getUserData().enqueue(object : retrofit2.Callback<List<Users>>{
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                listUsers.postValue(response.body())
            }
        })
    }

    val getHomeUsers : LiveData<List<Users>> = listUsers
}