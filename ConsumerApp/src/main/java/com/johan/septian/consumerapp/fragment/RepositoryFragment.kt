package com.johan.septian.consumerapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.johan.septian.consumerapp.DetailActivity
import com.johan.septian.consumerapp.R
import com.johan.septian.consumerapp.adapter.RepositoryAdapter
import com.johan.septian.consumerapp.model.Repository
import com.johan.septian.consumerapp.model.Users
import com.johan.septian.consumerapp.viewmodel.RepositoryViewModel
import kotlinx.android.synthetic.main.fragment_repository.*

class RepositoryFragment : Fragment() {

    private lateinit var repositoryViewModel: RepositoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_repository.setHasFixedSize(true)
        setRepository()
        rv_repository.layoutManager = LinearLayoutManager(context)
    }

    private fun setRepository() {
        val user = activity?.intent?.getParcelableExtra<Users>(DetailActivity.EXTRA_USERNAME)

        repositoryViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(RepositoryViewModel::class.java)
        context?.let { repositoryViewModel.loadRepository(it,user?.login) }
        repositoryViewModel.getRepository.observe(viewLifecycleOwner, Observer {
            rv_repository.adapter = RepositoryAdapter(context, it as ArrayList<Repository>)
            Log.d("ResponApi",it.toString())
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarRepository.visibility = View.VISIBLE
        } else {
            progressBarRepository.visibility = View.GONE
        }
    }

}