package com.utflnx.addressbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utflnx.addressbook.Utils.FORM_DATA_ITEM
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        intent.extras?.getSerializable(FORM_DATA_ITEM).let {
            renderData(it as UserModel)
        }
    }

    private fun renderData(userModel: UserModel) {
        tv_name.text = "${userModel.id} ${userModel.name}"
        tv_email.text = userModel.email
        tv_phone.text = userModel.phone
        tv_birth.text = userModel.birthDate
    }
}