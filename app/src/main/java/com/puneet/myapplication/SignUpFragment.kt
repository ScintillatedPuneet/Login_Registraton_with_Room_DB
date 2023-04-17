package com.puneet.myapplication

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64


class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val spinner: Spinner = view.findViewById(R.id.spinner_country)
        val spinnerAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.country)
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(spinnerAdapter)

        val db =
            Room.databaseBuilder(requireContext(), MyDatabase::class.java, "my_database").build()
        val userDao = db.userdao()
        val btnSignUp: Button = view.findViewById(R.id.btn_sign_up)
        val first_name: EditText = view.findViewById(R.id.edt_first_name)
        val last_name: EditText = view.findViewById(R.id.edt_last_name)
        val dateofbirth: EditText = view.findViewById(R.id.edt_dob)
        val user_name: EditText = view.findViewById(R.id.edt_username)
        val user_password: EditText = view.findViewById(R.id.edt_password)
        val phone_number: EditText = view.findViewById(R.id.edt_phone_number)
        val country: Spinner = view.findViewById(R.id.spinner_country)
        val confirm_password: EditText = view.findViewById(R.id.edt_confirm_password)

        val phoneRegex = Regex("^[1-9]\\d{9}$|^0[1-9]\\d{9}$")
        val phone_Regex = Regex("^\\+91[1-9]\\d{9}$|^0[1-9]\\d{9}$")



        btnSignUp.setOnClickListener {
            val firstName = first_name.text.toString()
            val lastName = last_name.text.toString()
            val dob = dateofbirth.text.toString()
            val userName = user_name.text.toString()

            val confirmPassword = confirm_password.text.toString()
            val phone = phone_number.text.toString()
            val id: Int = 0
            val countryName = country.selectedItem.toString()
            val imageByteArray: ByteArray? = null

            val password = user_password.text.toString()

            val salt = ByteArray(16)
            val random = SecureRandom()
            random.nextBytes(salt)
            val saltValue = Base64.getEncoder().encodeToString(salt)

            val saltedPassword = password + salt

            // Hash the salted password using SHA-256
            val sha256 = MessageDigest.getInstance("SHA-256")
            val hashBytes = sha256.digest(saltedPassword.toByteArray(StandardCharsets.UTF_8))
            val hashedPassword = hashBytes.joinToString("") { "%02x".format(it) }





            if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || userName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Display an error message to the user

                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT)
                    .show()
            } else if (confirmPassword != password) {
                Toast.makeText(requireContext(), "Password does not match", Toast.LENGTH_LONG)
                    .show()

            } else if (!(phoneRegex.matches(phone) || phone_Regex.matches(phone))) {
                Toast.makeText(requireContext(), "Please Check Phone Number", Toast.LENGTH_SHORT)
                    .show()
            } else {
                GlobalScope.launch {
                    val userExist = userDao.getUserByUsername(userName)
                    Log.d("SignUpFragment", "userExist: $userExist")
                    if (userExist != null) {
                        activity?.runOnUiThread {
                            Toast.makeText(
                                requireContext(),
                                "Username not available",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                    } else {
                        Log.d("SignUpFragment", "user does not exist")
                        val user = User(
                            id,
                            firstName,
                            lastName,
                            dob,
                            phone,
                            userName,
                            password,
                            countryName,
                            imageByteArray
                        )


                        val userTable = UserTable(
                            user.id,
                            user.firstName,
                            user.lastName,
                            user.dob,
                            user.phone,
                            user.userName,
                            user.password,
                            user.countryName,
                            user.imageByteArray

                        )

                        GlobalScope.launch {

                            userDao.insertUser(userTable)
                        }
                        activity?.runOnUiThread {
                            first_name.text.clear()
                            last_name.text.clear()
                            dateofbirth.text.clear()
                            user_name.text.clear()
                            user_password.text.clear()
                            confirm_password.text.clear()
                            phone_number.text.clear()

                            Toast.makeText(
                                requireContext(),
                                "User created successfully",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        }

        return view
    }
    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val first_name: EditText = view.findViewById(R.id.edt_first_name)
        val last_name: EditText = view.findViewById(R.id.edt_last_name)
        val dateofbirth: EditText = view.findViewById(R.id.edt_dob)
        val user_name: EditText = view.findViewById(R.id.edt_username)
        val user_password: EditText = view.findViewById(R.id.edt_password)
        val phone_number: EditText = view.findViewById(R.id.edt_phone_number)
        val country: Spinner = view.findViewById(R.id.spinner_country)
        val confirm_password: EditText = view.findViewById(R.id.edt_confirm_password)

        outState.putString("firstName", first_name.text.toString())
        outState.putString("lastName", last_name.text.toString())
        outState.putString("dob", dateofbirth.text.toString())
        outState.putString("userName", user_name.text.toString())
        outState.putString("password", user_password.text.toString())
        outState.putString("confirmPassword", confirm_password.text.toString())
        outState.putString("phone", phone_number.text.toString())
        outState.putString("countryName", country.selectedItem.toString())
    }*/


    /*private fun setEditTextError(editText: EditText) {
        val errorColor = Color.parseColor("#FF0000") // red color
        editText.background.mutate().setColorFilter(errorColor, PorterDuff.Mode.SRC_ATOP)
    }*/

}


/*val db = Room.databaseBuilder(Context, MyDatabase::class.java, "my_database").build()
        *//*
*/
/*val db = (applicationContext as MyApp).db*//*
*/
/*

        val userDao = db.userdao()

        // Get the root layout of your activity or fragment
        val rootLayout = findViewById<View>(R.id.main)

        rootLayout.setOnTouchListener { v, event ->
            // Hide the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            false
        }
          val  view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val btnSignUp: Button = findViewById(R.id.btn_sign_up)
        val first_name: EditText = findViewById(R.id.edt_first_name)
        val last_name: EditText = findViewById(R.id.edt_last_name)
        val dateofbirth: EditText = findViewById(R.id.edt_dob)
        val user_name: EditText = findViewById(R.id.edt_username)
        val user_password: EditText = findViewById(R.id.edt_password)
        val phone_number: EditText = findViewById(R.id.edt_phone_number)
        val country:Spinner=findViewById(R.id.spinner_country)
        val confirm_password: EditText =findViewById(R.id.edt_confirm_password)

        val phoneRegex = Regex("^[1-9]\\d{9}$|^0[1-9]\\d{9}$")
        val phone_Regex = Regex("^\\+91[1-9]\\d{9}$|^0[1-9]\\d{9}$")


        btnSignUp.setOnClickListener {

            val firstName = first_name.text.toString()
            val lastName = last_name.text.toString()
            val dob = (dateofbirth.text.toString()).toInt()
            val userName = user_name.text.toString()
            val password = user_password.text.toString()
            val confirmPassword = confirm_password.text.toString()
            val phone = phone_number.text.toString()
            val id: Int = 0
            val country = country.selectedItem.toString()


            if (firstName.isEmpty() || lastName.isEmpty() || dob==0 || userName.isEmpty()||password.isEmpty() || confirmPassword.isEmpty()) {
                // Display an error message to the user

                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else if (confirmPassword != password) {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show()

            }else if (!(phoneRegex.matches(phone) || phone_Regex.matches(phone))){
                Toast.makeText(this, "Please Check Phone Number", Toast.LENGTH_SHORT).show()
            }
            else {
                val user = User(id, firstName, lastName, dob, phone, userName, password, country)

                val userTable = UserTable(
                    user.id,
                    user.firstName,
                    user.lastName,
                    user.dob,
                    user.phone,
                    user.userName,
                    user.password,
                    user.country
                )

                GlobalScope.launch {
                    userDao.insertUser(userTable)
                }

                first_name.text.clear()
                last_name.text.clear()
                dateofbirth.text.clear()
                user_name.text.clear()
                user_password.text.clear()
                confirm_password.text.clear()
                phone_number.text.clear()

// Show a success message to the user
                Toast.makeText(this, "User created successfully", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun getdbdata(db: MyDatabase) {
        GlobalScope.launch {
            var userdetails = db.userdao().readAllData()
            Log.e("tag", "" + userdetails.value)
        }
    }
    fun clearEditTextFields(viewGroup: ViewGroup) {
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            if (child is ViewGroup) {
                // Recursively clear the fields of child ViewGroups
                clearEditTextFields(child)
            } else if (child is EditText) {
                // Clear the text of EditText views
                child.text.clear()
            }
        }
    }

        return view*//*



*/
