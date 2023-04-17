package com.puneet.myapplication


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import android.Manifest
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class BaseActivity : AppCompatActivity() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 1
        private val CAMERA_REQUEST_CODE = 100
        private val MY_CAMERA_REQUEST_CODE = 300

    }


    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var profileAgentImageView: ImageView
    private lateinit var cardView: CardView

    private var imageByteArray: ByteArray? = null

    private lateinit var lastNameEditBtn: ImageButton
    private lateinit var dobEditBtn: ImageButton
    private lateinit var phoneEditBtn: ImageButton
    private lateinit var usernameEditBtn: ImageButton
    private lateinit var passwordEditBtn: ImageButton
    private lateinit var firstNameEditBtn: ImageButton
    private lateinit var countryEditBtn: ImageButton
    private lateinit var saveBtn: Button
    private lateinit var cancleBtn: Button
    private lateinit var uploadImgBtn: ImageButton


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
        profileAgentImageView = findViewById(R.id.img_agent)

        firstNameEditBtn = findViewById(R.id.first_name_img_btn)
        lastNameEditBtn = findViewById(R.id.last_name_img_btn)
        dobEditBtn = findViewById(R.id.dob_img_btn)
        phoneEditBtn = findViewById(R.id.phone_number_img_btn)
        usernameEditBtn = findViewById(R.id.user_name_img_btn)
        passwordEditBtn = findViewById(R.id.password_img_btn)
        countryEditBtn = findViewById(R.id.country_img_btn)

        saveBtn = findViewById(R.id.btn_save)
        cancleBtn = findViewById(R.id.btn_cancle)
        uploadImgBtn = findViewById(R.id.btn_update_profile)


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

        /* cardView = findViewById(R.id.card_view)
         cardView.setBackgroundResource(R.drawable.baseline_crop_din_24)
 */


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


        /*uploadImgBtn.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an option")
                .setItems(arrayOf("Camera", "Gallery"),
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (which == 0) {
                            // TODO: Open camera
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED
                            ) {
                                // Request camera permission
                                ActivityCompat.requestPermissions(
                                    this,
                                    new String []{ Manifest.permission.CAMERA },
                                    MY_CAMERA_REQUEST_CODE
                                );
                            } else {
                                // TODO: Open gallery
                                val intent = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                startActivityForResult(intent, GALLERY_REQUEST_CODE)
                            }
                        }
                    })
            val dialog: AlertDialog = builder.create()
            dialog.show()




        }*/

        uploadImgBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Choose an option")
                .setItems(arrayOf("Camera", "Gallery"),
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (which == 0) {
                            // Open camera
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED
                            ) {
                                // Request camera permission
                                ActivityCompat.requestPermissions(
                                    this,
                                    arrayOf(Manifest.permission.CAMERA),
                                    MY_CAMERA_REQUEST_CODE
                                )
                            } else {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                startActivityForResult(intent, CAMERA_REQUEST_CODE)
                            }
                        } else {
                            // Open gallery
                            val intent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            startActivityForResult(intent, GALLERY_REQUEST_CODE)
                        }
                    })
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                    })
            val dialog: AlertDialog = builder.create()
            dialog.show()
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
                Room.databaseBuilder(application, MyDatabase::class.java, "my_database")
                    .fallbackToDestructiveMigration().build()
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

                if (user.imageByteArray != null) {
                    val imageBitmap = BitmapFactory.decodeByteArray(
                        user.imageByteArray,
                        0,
                        user.imageByteArray!!.size
                    )
                    profileAgentImageView.setImageBitmap(imageBitmap)

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            // Handle the camera result
            val extras = data?.extras
            val imageBitmap = extras?.get("data") as Bitmap?
            val photoFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_photo.jpg")

            try {
                val outputStream = FileOutputStream(photoFile)
                imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                profileAgentImageView.setImageBitmap(imageBitmap)
                // TODO: Do something with the saved photo file
                val inputStream = FileInputStream(photoFile)
                 imageByteArray = inputStream.buffered().use { it.readBytes() }

            } catch (e: Exception) {
                e.printStackTrace()
            }


            // TODO: Handle the captured image
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            // Handle the gallery result
            val imageUri = data.data
            // TODO: Handle the selected image from the gallery
            profileAgentImageView.setImageURI(imageUri)
            // Get the image data as a byte array
            val inputStream = imageUri?.let { contentResolver.openInputStream(it) }
            imageByteArray = inputStream?.buffered()?.use { it.readBytes() }
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
        val db = Room.databaseBuilder(application, MyDatabase::class.java, "my_database")
            .fallbackToDestructiveMigration().build()
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
                phone = phone.takeIf { it.isNotEmpty() } ?: currentUser.phone,
                imageByteArray = imageByteArray ?: currentUser.imageByteArray

            )

            Log.d("1", "" + user)
            userDao.update(user)
            Log.d("1", "" + user)
        }
    }
}


private fun resetFields() {
    val username = intent.getStringExtra("username") ?: ""

    GlobalScope.launch {
        val db = Room.databaseBuilder(application, MyDatabase::class.java, "my_database")
            .fallbackToDestructiveMigration().build()
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

                Glide.with(this@BaseActivity)
                    .load(user.imageByteArray)
                    .into(profileAgentImageView)


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
