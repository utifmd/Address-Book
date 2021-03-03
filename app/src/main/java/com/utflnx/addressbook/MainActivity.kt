package com.utflnx.addressbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.utflnx.addressbook.Utils.DEFAULT_DATA_REQ_CODE
import com.utflnx.addressbook.Utils.FORM_DATA
import com.utflnx.addressbook.Utils.FORM_DATA_ITEM
import com.utflnx.addressbook.Utils.FORM_DATA_ITEM_POS
import com.utflnx.addressbook.Utils.FORM_TYPE
import com.utflnx.addressbook.Utils.TYPE_SAVE_FORM
import com.utflnx.addressbook.Utils.TYPE_UPDATE_FORM
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), BookPresenter {
    private val TAG = javaClass.simpleName
    private lateinit var mAdapter: BookAdapter
    private var defaultItems = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBookAddressList()
    }

    private fun setUpBookAddressList() {
        mAdapter = BookAdapter(this)
        mAdapter.setItemListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

        fab_new.setOnClickListener(this::navigateNewBook)
        onItemsChangedListener(defaultItems)
    }

    override fun onItemViewClickListener(userModel: UserModel) {
        Log.d(TAG, "onItemClickListener: ${userModel.name} ${userModel.email}")
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(FORM_DATA_ITEM, userModel)
        })
    }

    private fun navigateNewBook(view: View) {
        startActivityForResult(Intent(this, SubmitActivity::class.java).apply {
            putExtra(FORM_TYPE, TYPE_SAVE_FORM)
            putExtra(FORM_DATA, defaultItems)
        }, DEFAULT_DATA_REQ_CODE)
    }

    override fun onItemEditClickListener(userModel: UserModel) {
        Log.d(TAG, "onItemEditClickListener()")
        startActivityForResult(Intent(this, SubmitActivity::class.java).apply {
            putExtra(FORM_TYPE, TYPE_UPDATE_FORM)
            putExtra(FORM_DATA, defaultItems)
            putExtra(FORM_DATA_ITEM, userModel)
        }, DEFAULT_DATA_REQ_CODE)
    }

    override fun onItemDeleteClickListener(position: Int) {
        Log.d(TAG, "onItemDeleteClickListener: $position")
        defaultItems.removeAt(position)
        onItemsChangedListener(defaultItems)
    }

    override fun onItemsChangedListener(userModels: List<UserModel>) {
        Log.d(TAG, "onItemsChangedListener")
        defaultItems = userModels as ArrayList<UserModel>
        mAdapter.setData(userModels)
        mRecyclerView.adapter = mAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEFAULT_DATA_REQ_CODE && resultCode == RESULT_OK){
            data?.extras?.getSerializable(FORM_DATA).let {
                if (it != null) {
                    defaultItems = it as ArrayList<UserModel>
                    onItemsChangedListener(defaultItems)
                }
            }
        }
    }
}