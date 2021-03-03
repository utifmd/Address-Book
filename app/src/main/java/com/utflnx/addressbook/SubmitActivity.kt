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
import com.utflnx.addressbook.Utils.FORM_DATA_ITEM
import com.utflnx.addressbook.Utils.FORM_DATA_ITEM_POS
import com.utflnx.addressbook.Utils.FORM_TYPE
import com.utflnx.addressbook.Utils.TYPE_SAVE_FORM
import com.utflnx.addressbook.Utils.TYPE_UPDATE_FORM
import kotlinx.android.synthetic.main.activity_input.*


class SubmitActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private var defaultItems = ArrayList<UserModel>()
    private var defaultModel: UserModel? = null
    private var mPosition: Int? = 0

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
            bundle?.getSerializable(FORM_DATA_ITEM).let {
                if (it != null) renderData(it as UserModel)
            }
            bundle?.getSerializable(FORM_DATA_ITEM_POS).let {
                if (it != null) mPosition = it as Int
            }
        }
    }

    private fun renderData(userModel: UserModel) {
        full_name.setText(userModel.name)
        phone.setText(userModel.phone)
        email.setText(userModel.email)
        birthDate.setText(userModel.birthDate)
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
        if (full_name.text.trim().isEmpty() || phone.text.trim().isEmpty() || email.text.trim().isEmpty() || birthDate.text.trim().isEmpty()){
            Snackbar.make(view, "Form can't be empty!", Snackbar.LENGTH_SHORT).show()
        }else{
//            defaultItems[mPosition!!] = UserModel(
//                name = full_name.text.trim().toString(),
//                phone = phone.text.trim().toString(),
//                email = email.text.trim().toString(),
//                birthDate = birthDate.text.trim().toString()
//            )

            setResult(RESULT_OK, Intent().apply {
                putExtra(FORM_DATA, defaultItems)
            }).let {
                finish()
            }
        }
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