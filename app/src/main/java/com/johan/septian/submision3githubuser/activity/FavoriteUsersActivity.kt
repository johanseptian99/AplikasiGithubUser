package com.johan.septian.submision3githubuser.activity

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.adapter.UserHomeAdapter
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.CONTENT_URI_USERS
import com.johan.septian.submision3githubuser.databinding.ActivityFavoriteBinding
import com.johan.septian.submision3githubuser.mapping.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUsersFav.setHasFixedSize(true)
        supportActionBar?.title = getString(R.string.user_favorite)

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
                binding.rvUsersFav.adapter = UserHomeAdapter(this@FavoriteUsersActivity, users)
            } else {
                showSnackBarMessage("Tidak ada data saat ini")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserAsync()
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvUsersFav, message, Snackbar.LENGTH_SHORT).show()
    }

}