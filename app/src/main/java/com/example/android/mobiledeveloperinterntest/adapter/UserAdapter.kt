package com.example.android.mobiledeveloperinterntest.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mobiledeveloperinterntest.R
import com.example.android.mobiledeveloperinterntest.models.User
import com.bumptech.glide.Glide
import com.example.android.mobiledeveloperinterntest.models.UserData

class UserAdapter(
    private val userList: ArrayList<UserData>,
    private val listener: OnAdapterListener
)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar : ImageView = itemView.findViewById(R.id.user_avatar)
        val name : TextView = itemView.findViewById(R.id.user_name)
        val email : TextView = itemView.findViewById(R.id.user_email)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_user, parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(list: List<UserData>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        val imgUrl = user.avatar
        Glide.with(holder.itemView.context)
            .load(imgUrl)
            .circleCrop()
            .into(holder.avatar)
        val fullName = "${user.firstName} ${user.lastName}"
        holder.name.text = fullName
        holder.email.text = user.email
        holder.itemView.setOnClickListener {
            listener.onClick(user)
        }

    }

    interface OnAdapterListener {
        fun onClick(user: UserData)
    }

}
