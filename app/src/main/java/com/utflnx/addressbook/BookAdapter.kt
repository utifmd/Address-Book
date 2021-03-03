package com.utflnx.addressbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var mListBookUsers: List<UserModel>
    lateinit var mBookPresenter: BookPresenter

    fun setData(list: List<UserModel>){
        mListBookUsers = list

        notifyDataSetChanged()
    }

    fun setItemListener(bookPresenter: BookPresenter){
        mBookPresenter = bookPresenter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GeneralViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_address_book, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userModel = mListBookUsers[position]

        (holder as GeneralViewHolder).bind(userModel, position, mBookPresenter)
    }

    override fun getItemCount(): Int {
        return mListBookUsers.size
    }

    inner class GeneralViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(userModel: UserModel, position: Int, mBookPresenter: BookPresenter) {
            itemView.findViewById<TextView>(R.id.full_name).text = userModel.name
            itemView.findViewById<TextView>(R.id.email).text = userModel.email

            itemView.setOnClickListener{ mBookPresenter.onItemClickListener(userModel) }
        }

    }

}
