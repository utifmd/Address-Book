package com.utflnx.addressbook

interface BookPresenter {
    fun onItemViewClickListener(userModel: UserModel)
    fun onItemDeleteClickListener(position: Int)
    fun onItemEditClickListener(userModel: UserModel)

    fun onItemsChangedListener(userModels: List<UserModel>)
}