package com.puneet.myapplication


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserTableDao {

    @Insert
    suspend fun insertUser(user: UserTable)

    @Update
    suspend fun update(user: UserTable)

    @Delete
    suspend fun delete(user: UserTable)

    @Query("SELECT * FROM userTable WHERE userName = :username AND password = :password")
    fun getUserByUsernameAndPassword(username: String, password: String): UserTable?


    @Query("SELECT * FROM userTable WHERE userName = :username")
    fun getUserByUsername(username: String): UserTable?



    }


/*@Query("SELECT * FROM userTable WHERE username = :username")
    fun getByUsername(username: String): LiveData<User>*/

/*   @Query("SELECT id, firstName, lastName, dob, phone, userName, password, country FROM users WHERE userName = :username")
   fun getUserByUsername(username: String): UserTable?*/

    /* @Query("SELECT * FROM users")
     suspend fun getAll(): List<UserTable>*/


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

