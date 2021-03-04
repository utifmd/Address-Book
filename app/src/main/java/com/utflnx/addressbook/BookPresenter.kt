package com.utflnx.addressbook

interface BookPresenter {
    fun onItemViewClickListener(userModel: UserModel)
    fun onItemDeleteClickListener(userModel: UserModel)
    fun onItemEditClickListener(userModel: UserModel)

    fun onItemsChangedListener(isUpdateState: Boolean, userModels: List<UserModel>)
}