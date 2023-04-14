package com.puneet.myapplication
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.*


class BaseActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var countryEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        firstNameEditText = findViewById(R.id.first_name_edt)
        lastNameEditText = findViewById(R.id.last_name_edt)
        dobEditText = findViewById(R.id.dob_edt)
        phoneEditText = findViewById(R.id.phone_number_edt)
        usernameEditText = findViewById(R.id.user_name_edt)
        passwordEditText = findViewById(R.id.password_edt)
        countryEditText = findViewById(R.id.country_edt)

        val username = intent.getStringExtra("username") ?: ""
        Log.d("BaseActivity", "Username: $username")

        /*val password = intent.getStringExtra("password")
        Log.d("BaseActivity", "password: $password")
*/
        GlobalScope.launch {
            val db =
                Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()
            val userDao = db.userdao()
            val user = userDao.getUserByUsername(username)
            runOnUiThread {
                /*val userDao = (Activity() as MainActivity).db.userdao()*/

                if (user != null) {
                    firstNameEditText.setText(user.firstName)
                    lastNameEditText.setText(user.lastName)
                    dobEditText.setText(user.dob)
                    phoneEditText.setText(user.phone)
                    usernameEditText.setText(user.userName)
                    passwordEditText.setText(user.password)
                    countryEditText.setText(user.countryName)
                }
            }

        }
    }
}