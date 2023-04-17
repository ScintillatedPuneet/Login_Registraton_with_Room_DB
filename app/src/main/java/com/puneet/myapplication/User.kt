package com.puneet.myapplication

data class User(
    val id:Int = 0,
    val firstName: String,
    val lastName: String,
    val dob: String,
    val phone: String,
    val userName: String,
    val password: String,
    val countryName:String,
    val imageByteArray: ByteArray?
)


