package com.utflnx.addressbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.utflnx.addressbook.Utils.DEFAULT_DATA_REQ_CODE
import com.utflnx.addressbook.Utils.FORM_DATA
import com.utflnx.addressbook.Utils.FORM_DATA_ITEM
import com.utflnx.addressbook.Utils.FORM_TYPE
import com.utflnx.addressbook.Utils.TYPE_SAVE_FORM
import com.utflnx.addressbook.Utils.TYPE_UPDATE_FORM
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity: AppCompatActivity(), BookPresenter, SearchView.OnQueryTextListener {
    private val TAG = javaClass.simpleName
    private lateinit var mAdapter: BookAdapter
    private var defaultItems = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mToolbar)
        setUpBookAddressList()
    }

    private fun setUpBookAddressList() {
        mAdapter = BookAdapter(this)
        mAdapter.setItemListener(this)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)

        mFabCreate.setOnClickListener(this::navigateNewBook)
        onItemsChangedListener(false, defaultItems)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        (menu?.findItem(R.id.appSearchBar)?.actionView as SearchView).let {
            it.queryHint = resources.getString(R.string.search)
            it.setOnQueryTextListener(this)
            return true
        }
    }

    private fun onEmptyItems() {
        mEmptyView.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }
    private fun onFilledItems() {
        mEmptyView.visibility = View.GONE
        mRecyclerView.visibility = View.VISIBLE
    }

    override fun onItemViewClickListener(userModel: UserModel) {
        Log.d(TAG, "onItemClickListener: ${userModel.name} ${userModel.email}")
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(FORM_DATA_ITEM, userModel)
        })
    }

    override fun onQueryTextSubmit(query: String?) = false

    override fun onQueryTextChange(query: String?): Boolean {
        if (query!!.trim().isNotEmpty())
            onItemsChangedListener(false, defaultItems.filter {
                it.name.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
            })
        else onItemsChangedListener(false, defaultItems)

        return true
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

    override fun onItemDeleteClickListener(userModel: UserModel) {
        Log.d(TAG, "onItemDeleteClickListener")
        defaultItems.remove(userModel)
        onItemsChangedListener(true, defaultItems)
    }

    override fun onItemsChangedListener(isUpdateState: Boolean, userModels: List<UserModel>) {
        if(isUpdateState) defaultItems = userModels as ArrayList<UserModel>

        mAdapter.setData(userModels)
        mRecyclerView.adapter = mAdapter

        if (userModels.isNotEmpty()) onFilledItems()
        else onEmptyItems()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DEFAULT_DATA_REQ_CODE && resultCode == RESULT_OK){
            data?.extras?.getSerializable(FORM_DATA).let {
                if (it != null) {
                    defaultItems = it as ArrayList<UserModel>
                    onItemsChangedListener(true, defaultItems)
                }
            }
        }
    }
}