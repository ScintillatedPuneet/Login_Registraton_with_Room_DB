package com.puneet.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserTableDao {

    @Insert
    suspend fun insertUser(user: UserTable)

   /* @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserTable>*/

    @Query("SELECT * FROM userTable ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserTable>>
}
    /*@Query("SELECT EXISTS (SELECT * from UserTable where userName=:userName)")
    fun isTaken(userName: String): Boolean

    @Query("SELECT EXISTS (SELECT * from UserTable where userName=:userName AND password=:password )")
    fun login(userName: String,password:String, dob :Int)

    @Delete
    suspend fun deleteUser(userTable: UserTable)*/
/*
   @Query("SELECT * FROM userTable ORDER BY firstName ASC")
    fun getUserOrderedByFirstName(): Flow<List<UserTable>>

    @Query("SELECT * FROM contact ORDER BY lastName ASC")
    fun getContactsOrderedByLastName(): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun getContactsOrderedByPhoneNumber(): Flow<List<Contact>*/

