package com.johan.septian.submision3githubuser.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.johan.septian.submision3githubuser.activity.DetailActivity
import com.johan.septian.submision3githubuser.R
import com.johan.septian.submision3githubuser.adapter.UserHomeAdapter
import com.johan.septian.submision3githubuser.model.Users
import com.johan.septian.submision3githubuser.viewmodel.FollowViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_following.setHasFixedSize(true)
        setFollowing()
        rv_following.layoutManager = LinearLayoutManager(context)
    }

    private fun setFollowing() {
        val user = activity?.intent?.getParcelableExtra<Users>(DetailActivity.EXTRA_USERNAME)

        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)
        context?.let { followViewModel.loadFollowingUsers(it,user?.login,"1") }
        followViewModel.getFollowingUsers.observe(viewLifecycleOwner, Observer {
            rv_following.adapter = UserHomeAdapter(context, it)
            Log.d("ResponApi",it.toString())
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFollowing.visibility = View.VISIBLE
        } else {
            progressBarFollowing.visibility = View.GONE
        }
    }
}