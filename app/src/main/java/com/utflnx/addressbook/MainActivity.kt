package com.utflnx.addressbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utflnx.addressbook.Utils.DEFAULT_DATA_REQ_CODE
import com.utflnx.addressbook.Utils.FORM_DATA
import com.utflnx.addressbook.Utils.FORM_TYPE
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

        fab_new.setOnClickListener(this::navigateToSubmission)
        onItemsChangedListener(defaultItems)
    }

    private fun navigateToSubmission(view: View) {
        startActivityForResult(Intent(this, InputActivity::class.java).apply {
            putExtra(FORM_TYPE, Utils.TYPE_SAVE_FORM)
            putExtra(FORM_DATA, defaultItems)
        }, DEFAULT_DATA_REQ_CODE)
    }

    override fun onItemClickListener(userModel: UserModel) {
        Log.d(TAG, "onItemClickListener: ${userModel.name} ${userModel.email}")
    }

    override fun onItemsChangedListener(userModels: List<UserModel>) {
        defaultItems = userModels as ArrayList<UserModel>
        Log.d(TAG, "onItemsChangedListener")
        mAdapter.setData(userModels)
        mRecyclerView.adapter = mAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEFAULT_DATA_REQ_CODE && resultCode == RESULT_OK){
            data?.extras?.getSerializable(FORM_DATA).let {
                if (it != null) onItemsChangedListener(it as ArrayList<UserModel>)
            }
        }
    }
}