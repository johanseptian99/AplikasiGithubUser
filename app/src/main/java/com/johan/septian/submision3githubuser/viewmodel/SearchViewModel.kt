package com.johan.septian.submision3githubuser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.johan.septian.submision3githubuser.model.SearchUsers
import com.johan.septian.submision3githubuser.model.Users
import com.johan.septian.submision3githubuser.retrofit.RetroConfig
import retrofit2.Call
import retrofit2.Response

class SearchViewModel : ViewModel() {
    private val listSearchUsers : MutableLiveData<List<Users>> = MutableLiveData()

    fun loadSearchUser(context: Context, query:String?){
        RetroConfig.getRetrofit().getSearchData(query).enqueue(object : retrofit2.Callback<SearchUsers>{
            override fun onFailure(call: Call<SearchUsers>, t: Throwable) {
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<SearchUsers>, response: Response<SearchUsers>) {
                if(response.body() != null){
                    listSearchUsers.postValue(response.body()?.items)
                }
            }
        })
    }

    val getListSearchUser : LiveData<List<Users>> = listSearchUsers
}