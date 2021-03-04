package com.utflnx.addressbook

import java.io.Serializable

data class UserModel(
    val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val birthDate: String
): Serializable