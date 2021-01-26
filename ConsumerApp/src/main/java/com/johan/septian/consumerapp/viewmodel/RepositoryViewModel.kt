package com.johan.septian.consumerapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johan.septian.consumerapp.model.Repository
import com.johan.septian.consumerapp.retrofit.RetroConfig
import retrofit2.Call
import retrofit2.Response

class RepositoryViewModel: ViewModel() {

    private val listRepository : MutableLiveData<List<Repository>> = MutableLiveData()

    fun loadRepository(context : Context, username:String?){
        RetroConfig.getRetrofit().getRepositoryData(username).enqueue(object : retrofit2.Callback<List<Repository>>{
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Repository>>, response: Response<List<Repository>>) {
                listRepository.value = response.body()
            }
        })
    }

    val getRepository : LiveData<List<Repository>> = listRepository
}