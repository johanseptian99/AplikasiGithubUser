package com.johan.septian.submision3githubuser.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.fragment.HomeFragment
import com.johan.septian.submision3githubuser.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarMain)
        val mFragmentManager = supportFragmentManager
        val mHomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + HomeFragment::class.java.simpleName)
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
                    .commit()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val mSearchFragment = SearchFragment()
                val mBundle = Bundle()
                mBundle.putString(SearchFragment.EXTRA_NAME, query)
                mSearchFragment .arguments = mBundle
                val mFragmentManager = supportFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(
                        R.id.frame_container,
                        mSearchFragment,
                        SearchFragment::class.java.simpleName
                    )
                    addToBackStack(null)
                    commit()
                }
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.setting){
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        } else if (item.itemId == R.id.user_favorite){
            startActivity(Intent(applicationContext, FavoriteUsersActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }

}