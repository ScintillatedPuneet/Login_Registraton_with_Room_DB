package com.puneet.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class loginfragment : Fragment() {

    lateinit var edit_id_txt: EditText
    lateinit var edit_password: EditText
    lateinit var userName: String
    lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_loginfragment, container, false)


        edit_id_txt = view.findViewById(R.id.edit_id_txt)
        edit_password = view.findViewById(R.id.edit_password)


        userName = edit_id_txt.getText().toString()
        password = edit_password.getText().toString()


        val cardView: CardView = view.findViewById(R.id.card_password)
        val passButton: ImageButton = view.findViewById(R.id.button_passview)
        val edtId: EditText = view.findViewById(R.id.edit_id_txt)



        passButton.setOnClickListener {
            val userName: String = edit_id_txt.getText().toString().trim()

            GlobalScope.launch {
                val user = checkUserExists(userName)
                Log.d("punit", "" + user)
                requireActivity().runOnUiThread {
                    if (user) {
                        Toast.makeText(requireContext(), "Correct LivePad ID", Toast.LENGTH_SHORT)
                            .show()

                        if (cardView.visibility == View.GONE) {

                            // Expand the card
                            val slideDown =
                                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down)
                            slideDown.setAnimationListener(object : Animation.AnimationListener {
                                override fun onAnimationStart(animation: Animation?) {
                                    cardView.visibility = View.VISIBLE
                                }

                                override fun onAnimationEnd(animation: Animation?) {}

                                override fun onAnimationRepeat(animation: Animation?) {}
                            })
                            cardView.startAnimation(slideDown)
                            passButton.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(requireContext(), "Check LivePad ID", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }


        edtId.setOnClickListener {
            if (cardView.visibility == View.VISIBLE) {

                // Inside the button click listener for login button

                // Collapse the card
                val slideUp =
                    AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
                slideUp.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {}

                    override fun onAnimationEnd(animation: Animation?) {
                        cardView.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}
                })
                cardView.startAnimation(slideUp)
                passButton.visibility = View.VISIBLE
            }
        }

        val buttonpass = view.findViewById<ImageButton>(R.id.button_pass)
        buttonpass.setOnClickListener {

            GlobalScope.launch {

                userName = edit_id_txt.text.toString()
                password = edit_password.text.toString()

                Log.d("puni", "" + userName + password)
                val user = checkUserExists(userName, password)

                requireActivity().runOnUiThread {
                    if (user) {
                        // User found, proceed with login

                        val intent = Intent(requireActivity(), BaseActivity::class.java)
                        intent.putExtra("username", userName)
                        intent.putExtra("password", password)
                        Log.d("logActivity", "Username: $userName")
                        Log.d("logActivity", "Username: $password")
                        requireActivity().startActivity(intent)


                    } else {

                        // User not found, show error message
                        Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }

        return view
    }

    private fun checkUserExists(username: String, password: String? = null): Boolean {
        val userDao = (requireActivity() as MainActivity).db.userdao()
        val user = if (password == null) {
            userDao.getUserByUsername(username)
        } else {
            userDao.getUserByUsernameAndPassword(username, password)

        }
        return user != null
    }

    /*private fun checkUsernameExists(username: String): Boolean {

        // Query Room database to check if username exists
        val user =
            (requireActivity() as MainActivity).db.userdao().getUserByUsername(username)
        Log.d("puneet", "" + user)
        return user != null
    }

    private fun checkUsernamePasswordExists(username: String, password: String): Boolean {

        // Query Room database to check if username exists
        val user = (requireActivity() as MainActivity).db.userdao().getUserByUsernameAndPassword(username,password)
        Log.d("puneetpass", "" + user)
        return  user != null
    }*/


}