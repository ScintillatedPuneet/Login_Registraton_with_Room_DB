package com.puneet.myapplication

import androidx.lifecycle.Observer
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey


@Entity(tableName = "userTable")
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var firstName: String,
    var lastName: String,
    var dob: String,
    var phone: String,
    var userName: String,
    var password: String,
    var countryName :String
) {
    fun observe(baseActivity: BaseActivity, observer: Observer<User>) {

    }
}

