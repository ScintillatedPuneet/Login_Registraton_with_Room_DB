package com.puneet.myapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room


/*import kotlin.coroutines.jvm.internal.CompletedContinuation.context*/

class MainActivity : AppCompatActivity() {
    lateinit var db:MyDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db =
            Room.databaseBuilder(application, MyDatabase::class.java, "my_database").build()

        // Hide the navigation bar
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.hide();
        }

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, loginfragment())
            .commit()

        val signUp : TextView = findViewById(R.id.txt_sign_up)

        signUp.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SignUpFragment())
                .addToBackStack(null)
                .commit()

        }




        // Get the root layout of your activity or fragment
        val rootLayout = findViewById<View>(R.id.main)


        rootLayout.setOnTouchListener { v, event ->
            // Hide the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            false
        }





    }
}

           /* if (cardView.visibility == View.VISIBLE) {
                val animator =
                    ObjectAnimator.ofFloat(cardView, "translationY", 0f, -cardView.width.toFloat())
                animator.duration = 500 // Set animation duration in milliseconds


                // Set listener to hide view after animation completes

                // Set listener to hide view after animation completes
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {

                        cardView.visibility = View.GONE
                        cardView.translationY = 0f // Reset alpha for next use
                    }
                })

                // Start animation

                // Start animation
                animator.start()



            } else {
                passButton.visibility = View.GONE
                cardView.setVisibility(View.VISIBLE)

                // Create an animation to expand the layout

                // Create an animation to expand the layout
                val expandAnimation = ObjectAnimator.ofInt(
                    cardView, "height", 0, cardView.getHeight()
                )
                expandAnimation.duration = 500
                expandAnimation.start()

            }
        }*/




        // Inflate the layout for this fragment

       /* val spinner: Spinner = findViewById(R.id.spinner_country)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,resources.getStringArray(R.array.country))
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(spinnerAdapter)*/



           /* val user = UserTable(
                firstName = "John",
                lastName = "Doe",
                dob = "19900101",
                phone = 1234567890,
                userName = "johndoe",
                password = "password"
            )*/

            /*user.setFirstName("John")
        user.setLastName("Doe")
        user.setDob("19900101")
        user.setPhone(1234567890)
        user.setUserName("johndoe")
        user.setPassword("password")*/



        /*val db = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "my_database").build()
        val userDao = db.usertabledao

        val user = UserTable()
            user.setFirstName("John")
            user.setLastName("Doe")
            user.setDob("19900101")
            user.setPhone(1234567890)
            user.setUserName("johndoe")
            user.setPassword("password")


        GlobalScope.launch {
            userDao.upsertUser(user)
        }*/





