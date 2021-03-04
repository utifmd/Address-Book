package com.utflnx.addressbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_input.view.*
import kotlinx.android.synthetic.main.item_address_book.view.*
import kotlinx.android.synthetic.main.item_address_book.view.email
import kotlinx.android.synthetic.main.item_address_book.view.full_name

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
            itemView.full_name.text = userModel.name
            itemView.email.text = userModel.email

            itemView.icon_view.setOnClickListener{ mBookPresenter.onItemViewClickListener(userModel) }
            itemView.icon_delete.setOnClickListener { mBookPresenter.onItemDeleteClickListener(userModel) }
            itemView.icon_edit.setOnClickListener { mBookPresenter.onItemEditClickListener(userModel) }
        }

    }

}
