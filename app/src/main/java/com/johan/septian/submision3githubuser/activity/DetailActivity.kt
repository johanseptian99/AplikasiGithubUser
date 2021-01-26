package com.johan.septian.submision3githubuser.activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.adapter.TabsAdapter
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.AVATAR
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.CONTENT_URI_USERS
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.HTML
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.ID_USERS
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.TYPE
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.USERNAME
import com.johan.septian.submision3githubuser.mapping.MappingHelper
import com.johan.septian.submision3githubuser.model.Users
import com.johan.septian.submision3githubuser.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    companion object{
        const val EXTRA_USERNAME = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initData()
        initTabs()
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        val user = intent.getParcelableExtra<Users>(EXTRA_USERNAME)
        if (user != null) {

            detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
            detailViewModel.loadUser(applicationContext, user.login)
            detailViewModel.getDetailUser.observe(this, Observer {
                Glide.with(applicationContext)
                    .load(it.avatarUrl)
                    .into(findViewById(R.id.img_avatar_detail))
                supportActionBar?.title = it.login
                tv_name.text = it.name

                if (it.bio.isNullOrEmpty()) {
                    tv_bio.visibility = View.GONE
                } else {
                    tv_bio.text = it.bio
                    tv_bio.visibility = View.VISIBLE
                }
                if (it.email.isNullOrEmpty()) {
                    tv_email.visibility = View.GONE
                } else {
                    tv_email.text = it.email
                    tv_email.visibility = View.VISIBLE
                }

                val tRepository = getString(R.string.repository,it.publicRepos.toString())
                val tFollowers = getString(R.string.followers,it.followers.toString())
                val tFollowing = getString(R.string.following,it.following.toString())

                tv_repository.text = tRepository
                tv_followers.text = tFollowers
                tv_following.text = tFollowing
                fab_favorite.visibility = View.VISIBLE

            })

            loadUserFavAsync(user.id)

            val fabFavorite: View = findViewById(R.id.fab_favorite)
            fabFavorite.setOnClickListener { view ->
                val addSnackbar = getString(R.string.add_snackbar,user.login)
                Snackbar.make(view, addSnackbar, Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
                val values = ContentValues()
                values.put(ID_USERS, user.id)
                values.put(USERNAME, user.login)
                values.put(AVATAR, user.avatarUrl)
                values.put(HTML, user.htmlUrl)
                values.put(TYPE, user.type)
                contentResolver.insert(CONTENT_URI_USERS, values)
                fab_favorite.visibility = View.INVISIBLE
                fab_unfavorite.visibility = View.VISIBLE
            }

            val fabUnFavorite: View = findViewById(R.id.fab_unfavorite)
            fabUnFavorite.setOnClickListener { view ->
                val deleteSnackbar = getString(R.string.delete_snackbar,user.login)
                Snackbar.make(view, deleteSnackbar, Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
                contentResolver.delete(Uri.parse(CONTENT_URI_USERS.toString() + "/" + user.id), null, null)
                fab_favorite.visibility = View.VISIBLE
                fab_unfavorite.visibility = View.INVISIBLE
            }

        }
    }

    private fun loadUserFavAsync(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = contentResolver.query(Uri.parse(CONTENT_URI_USERS.toString() + "/" + id), null, null, null, null)
                MappingHelper.mapCursorUsersToList(cursor)
            }
            val users = deferredUsers.await()
            if (users.size > 0) {
                fab_favorite.visibility = View.GONE
                fab_unfavorite.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun initTabs(){
        val tabsAdapter = TabsAdapter(this, supportFragmentManager)
        view_pager.adapter = tabsAdapter
        tabs.setupWithViewPager(view_pager)
    }
}