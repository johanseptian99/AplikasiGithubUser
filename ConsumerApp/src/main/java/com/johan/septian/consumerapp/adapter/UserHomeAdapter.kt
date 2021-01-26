package com.johan.septian.consumerapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.johan.septian.consumerapp.DetailActivity
import com.johan.septian.consumerapp.R
import com.johan.septian.consumerapp.model.Users
import kotlinx.android.synthetic.main.user_layout.view.*

class UserHomeAdapter(private val context: Context?, private val listUser : List<Users>): RecyclerView.Adapter<UserHomeAdapter.CardViewViewHolder>() {

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Users) {
            with(itemView) {
                Glide.with(context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().override(100, 100))
                    .into(img_avatar)
                tv_username.text = user.login
                tv_url.text = user.htmlUrl
                tv_type.text = user.type
                tv_id.text = StringBuilder("ID: ${user.id}")
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_layout, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, listUser[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUser.size
}