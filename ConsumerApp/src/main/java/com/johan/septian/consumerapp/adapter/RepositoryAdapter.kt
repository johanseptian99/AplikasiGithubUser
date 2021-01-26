package com.johan.septian.consumerapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johan.septian.consumerapp.R
import com.johan.septian.consumerapp.model.Repository

import kotlinx.android.synthetic.main.repository_layout.view.*

class RepositoryAdapter(private val context: Context?, private val listRepo : ArrayList<Repository>): RecyclerView.Adapter<RepositoryAdapter.CardViewViewHolder>() {

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(repo: Repository) {
            with(itemView) {
                tv_repositoryname.text = repo.name
                tv_deskripsi.text = repo.description
                tv_language.text = repo.language
                tv_rating.text = repo.stargazersCount.toString()
                tv_dateupdate.text = repo.updatedAt
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.repository_layout, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.CardViewViewHolder, position: Int) {
        holder.bind(listRepo[position])
    }

    override fun getItemCount(): Int = listRepo.size
}