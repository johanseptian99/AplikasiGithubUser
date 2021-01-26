package com.johan.septian.consumerapp

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.johan.septian.consumerapp.adapter.UserHomeAdapter
import com.johan.septian.consumerapp.database.UsersContract.UsersColumns.Companion.CONTENT_URI_USERS
import com.johan.septian.consumerapp.databinding.ActivityMainBinding
import com.johan.septian.consumerapp.mapping.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUsersFav.setHasFixedSize(true)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI_USERS, true, myObserver)

        if (savedInstanceState == null) {
            loadUserAsync()
        }
        binding.rvUsersFav.layoutManager = LinearLayoutManager(this)
    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressbarUserFav.visibility = View.VISIBLE
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver.query(CONTENT_URI_USERS, null, null, null, null)
                MappingHelper.mapCursorUsersToList(cursor)
            }
            binding.progressbarUserFav.visibility = View.INVISIBLE
            val users = deferredUsers.await()
            if (users.size > 0) {
                binding.rvUsersFav.adapter = UserHomeAdapter(this@MainActivity, users)
            } else {
                showSnackBarMessage("Tidak ada data saat ini")
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvUsersFav, message, Snackbar.LENGTH_SHORT).show()
    }

}