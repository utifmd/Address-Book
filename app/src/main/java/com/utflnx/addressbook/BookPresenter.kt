package com.utflnx.addressbook

interface BookPresenter {
    fun onItemClickListener(userModel: UserModel)
    fun onItemsChangedListener(userModels: List<UserModel>)
}