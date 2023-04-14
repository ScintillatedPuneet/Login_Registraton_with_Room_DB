package com.puneet.myapplication


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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

    private lateinit var lastNameEditBtn: ImageButton
    private lateinit var dobEditBtn: ImageButton
    private lateinit var phoneEditBtn: ImageButton
    private lateinit var usernameEditBtn: ImageButton
    private lateinit var passwordEditBtn: ImageButton
    private lateinit var firstNameEditBtn: ImageButton
    private lateinit var countryEditBtn: ImageButton
    private lateinit var saveBtn: Button
    private lateinit var cancleBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)


        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        firstNameEditText = findViewById(R.id.first_name_edt)
        lastNameEditText = findViewById(R.id.last_name_edt)
        dobEditText = findViewById(R.id.dob_edt)
        phoneEditText = findViewById(R.id.phone_number_edt)
        usernameEditText = findViewById(R.id.user_name_edt)
        passwordEditText = findViewById(R.id.password_edt)
        countryEditText = findViewById(R.id.country_edt)

        firstNameEditBtn = findViewById(R.id.first_name_img_btn)
        lastNameEditBtn = findViewById(R.id.last_name_img_btn)
        dobEditBtn = findViewById(R.id.dob_img_btn)
        phoneEditBtn = findViewById(R.id.phone_number_img_btn)
        usernameEditBtn = findViewById(R.id.user_name_img_btn)
        passwordEditBtn = findViewById(R.id.password_img_btn)
        countryEditBtn = findViewById(R.id.country_img_btn)

        saveBtn = findViewById(R.id.btn_save)
        cancleBtn = findViewById(R.id.btn_cancle)


        firstNameEditText.isEnabled = false
        lastNameEditText.isEnabled = false
        dobEditText.isEnabled = false
        phoneEditText.isEnabled = false
        usernameEditText.isEnabled = false
        passwordEditText.isEnabled = false
        countryEditText.isEnabled = false

        val editTexts = listOf(
            firstNameEditText,
            lastNameEditText,
            dobEditText,
            phoneEditText,
            usernameEditText,
            passwordEditText,
            countryEditText
        )
        val imageButtons = listOf(
            firstNameEditBtn,
            lastNameEditBtn,
            dobEditBtn,
            phoneEditBtn,
            usernameEditBtn,
            passwordEditBtn,
            countryEditBtn

        )

        imageButtons.forEachIndexed { index, imageButton ->
            imageButton.setOnClickListener {
                // Enable/disable only the corresponding EditText field
                editTexts.forEachIndexed { innerIndex, editText ->
                    editText.isEnabled = innerIndex == index
                }
            }
        }

        loadUserData()

        saveBtn.setOnClickListener {
            saveUserData()
            Toast.makeText(baseContext, "User Updated", Toast.LENGTH_SHORT)
                .show()
            firstNameEditText.isEnabled = false
            lastNameEditText.isEnabled = false
            dobEditText.isEnabled = false
            phoneEditText.isEnabled = false
            usernameEditText.isEnabled = false
            passwordEditText.isEnabled = false
            countryEditText.isEnabled = false
        }

        // Set an OnClickListener on the cancel button
        cancleBtn.setOnClickListener {
            resetFields()
            Toast.makeText(baseContext, "No Changes", Toast.LENGTH_SHORT)
                .show()
            firstNameEditText.isEnabled = false
            lastNameEditText.isEnabled = false
            dobEditText.isEnabled = false
            phoneEditText.isEnabled = false
            usernameEditText.isEnabled = false
            passwordEditText.isEnabled = false
            countryEditText.isEnabled = false
        }


            val rootLayout = findViewById<View>(R.id.base)
            rootLayout.setOnTouchListener { v, event ->
                // Hide the keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                false
            }


    }
    private fun loadUserData() {
        val username = intent.getStringExtra("username") ?: ""

        GlobalScope.launch {
            val db =
                Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()
            val userDao = db.userdao()

            val user = userDao.getUserByUsername(username)
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

        private fun saveUserData() {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val countryName = countryEditText.text.toString()
            val dob = dobEditText.text.toString()
            val userName = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val phone = phoneEditText.text.toString()

            val username = intent.getStringExtra("username") ?: ""

            GlobalScope.launch {
                val db = Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()
                val userDao = db.userdao()

                val currentUser = userDao.getUserByUsername(username)
                if (currentUser != null) {
                    val user = currentUser.copy(
                        firstName = firstName.takeIf { it.isNotEmpty() } ?: currentUser.firstName,
                        lastName = lastName.takeIf { it.isNotEmpty() } ?: currentUser.lastName,
                        countryName = countryName.takeIf { it.isNotEmpty() } ?: currentUser.countryName,
                        dob = dob.takeIf { it.isNotEmpty() } ?: currentUser.dob,
                        userName = userName.takeIf { it.isNotEmpty() } ?: currentUser.userName,
                        password = password.takeIf { it.isNotEmpty() } ?: currentUser.password,
                        phone = phone.takeIf { it.isNotEmpty() } ?: currentUser.phone
                    )
                    Log.d("1",""+user)
                    userDao.update(user)
                    Log.d("1",""+user)
                }
            }
        }

    private fun resetFields() {
        val username = intent.getStringExtra("username") ?: ""

        GlobalScope.launch {
            val db = Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()
            val userDao = db.userdao()

            val user = userDao.getUserByUsername(username)

            runOnUiThread {
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


























/* val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val countryName = countryEditText.text.toString()
            val dob = dobEditText.text.toString()
            val userName = usernameEditText.toString()
            val password = passwordEditText.toString()
            val phone = phoneEditText.toString()*/


/*   val username = intent.getStringExtra("username") ?: ""
Log.d("BaseActivity", "Username: $username")
*//*val user =
                UserTable(2, firstName, lastName, countryName, dob, userName, password, phone)
            Log.d("p", "" + user)*//*

            GlobalScope.launch {

                val db =
                    Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()
                val userDao = db.userdao()

                val currentUser = userDao.getUserByUsername(username)
                if (currentUser != null) {
                    val user = currentUser.copy(
                        firstName = firstName.takeIf { it.isNotEmpty() } ?: currentUser.firstName,
                        lastName = lastName.takeIf { it.isNotEmpty() } ?: currentUser.lastName,
                        countryName = countryName.takeIf{ it.isNotEmpty() } ?: currentUser.countryName,
                        dob = dob.takeIf{ it.isNotEmpty() } ?: currentUser.dob,
                        userName = userName.takeIf{ it.isNotEmpty() } ?: currentUser.userName,
                        password = password.takeIf{ it.isNotEmpty() } ?: currentUser.password,
                        phone =  phone.takeIf{ it.isNotEmpty() } ?: currentUser.phone
                    )
                    userDao.update(user)
                }

            }
        }

        // Set an OnClickListener on the drawable of firstNameEditText




        val username = intent.getStringExtra("username") ?: ""
        *//*val password = intent.getStringExtra("password")
    Log.d("BaseActivity", "password: $password")
*//*
        GlobalScope.launch {

            val db =
                Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()
            val userDao = db.userdao()

            val user = userDao.getUserByUsername(username)
            runOnUiThread {
                *//*val userDao = (Activity() as MainActivity).db.userdao()*//*

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

        }*/
