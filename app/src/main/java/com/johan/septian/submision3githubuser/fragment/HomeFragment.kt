package com.johan.septian.submision3githubuser.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.adapter.UserHomeAdapter
import com.johan.septian.submision3githubuser.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_userhome.setHasFixedSize(true)
        setUsers()
        backTop()
        rv_userhome.layoutManager = LinearLayoutManager(context)
    }

    private fun setUsers() {
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            HomeViewModel::class.java
        )
        homeViewModel.loadHomeUsers(context)
        homeViewModel.getHomeUsers.observe(viewLifecycleOwner, Observer {
            rv_userhome.adapter = UserHomeAdapter(context, it)
            Log.d("ResponApi", it.toString())
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun backTop() {
        fabTop.setOnClickListener {
            rv_userhome.smoothScrollToPosition(0)
        }
    }
}