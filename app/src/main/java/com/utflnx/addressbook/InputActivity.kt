package com.utflnx.addressbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.utflnx.addressbook.Utils.DEFAULT_DATA_REQ_CODE
import com.utflnx.addressbook.Utils.FORM_DATA
import com.utflnx.addressbook.Utils.FORM_TYPE
import com.utflnx.addressbook.Utils.TYPE_SAVE_FORM
import com.utflnx.addressbook.Utils.TYPE_UPDATE_FORM
import kotlinx.android.synthetic.main.activity_input.*


class InputActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private var defaultItems = ArrayList<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        intent.extras.let { bundle ->
            bundle?.getString(FORM_TYPE).let {
                if (it != null) setUpViewType(it)
            }
            bundle?.getSerializable(FORM_DATA).let {
                if (it != null) defaultItems = it as ArrayList<UserModel>
            }
        }
    }

    private fun setUpViewType(typeView: String) {
         when(typeView){
             TYPE_SAVE_FORM -> {
                 btn_submit.text = resources.getString(R.string._save)
                 btn_submit.setOnClickListener(this::saveForm)
             }
             TYPE_UPDATE_FORM -> {
                 btn_submit.text = resources.getString(R.string._update)
                 btn_submit.setOnClickListener(this::updateForm)

             } else -> throw IllegalArgumentException("Invalid view")
        }
    }

    private fun updateForm(view: View) {
        Log.d(TAG, "updateForm: clicked")
    }

    private fun saveForm(view: View) {
        Log.d(TAG, "saveForm: clicked")
        if (full_name.text.trim().isEmpty() || phone.text.trim().isEmpty() || email.text.trim().isEmpty() || birthDate.text.trim().isEmpty()){
            Snackbar.make(view, "Form can't be empty!", Snackbar.LENGTH_SHORT).show()
        }else{
            defaultItems.add(UserModel(
                name = full_name.text.trim().toString(),
                phone = phone.text.trim().toString(),
                email = email.text.trim().toString(),
                birthDate = birthDate.text.trim().toString()
            ))

            setResult(RESULT_OK, Intent().apply {
                putExtra(FORM_DATA, defaultItems)
            }).let {
                finish()
            }
        }
    }
}