package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalmadd.databinding.ActivityCodiaMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var  binding: ActivityCodiaMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the initial layout
        setContentView(R.layout.container_splash_screen)

        // Transition to login screen after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.layout_get_started)

            // Handle container_button click to move to login screen
            val containerButton: FrameLayout = findViewById(R.id.container_button)
            containerButton.setOnClickListener {
                setContentView(R.layout.container_login_screen)

                // Handle login logic
                handleLogin()
            }
        }, 5000)
    }

    // Function to handle login logic
    private fun handleLogin() {
        // Get references to EditText and FrameLayout
        val emailField: EditText = findViewById(R.id.text_email)
        val passwordField: EditText = findViewById(R.id.text_password)
        val loginButton: FrameLayout = findViewById(R.id.container_button_login_user)

        // Set up a click listener for the login button
        loginButton.setOnClickListener {
            val enteredEmail = emailField.text.toString()
            val enteredPassword = passwordField.text.toString()

            // Replace these with your actual username and password
            val correctEmail = "1"
            val correctPassword = "1"

            // Check if the email and password match
            if (enteredEmail == correctEmail && enteredPassword == correctPassword) {
                // Correct login, move to the find doctor screen
                val intent = Intent(this, FindDoctorActivity::class.java)
                startActivity(intent)

                // Handle find_doctor_button click to navigate to another page
//                handleFindDoctorNavigation()
            } else {
                // Wrong username or password, show an alert (Toast message)
                Toast.makeText(this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to handle navigation after clicking find_doctor_button
// Function to handle navigation after clicking find_doctor_button
//    private fun handleFindDoctorNavigation() {
//        // Find the find_doctor_button
//        val findDoctorButton: FrameLayout = findViewById(R.id.find_doctor_button)
//
//        // Set up a click listener for the find_doctor_button
//        findDoctorButton.setOnClickListener {
//            // Navigate to the DoctorList activity using Intent
//            val intent = Intent(this, DoctorList::class.java)
//            startActivity(intent)
//        }
//
//        setupNavigation()
//
//    }
//
//
//    // Function to set up navigation for image views
//    private fun setupNavigation() {
//        val imageGroup3: ImageView = findViewById(R.id.image_group3)
//        val imageGroup1: ImageView = findViewById(R.id.image_group1)
//        val imageGroup2: ImageView = findViewById(R.id.image_group2)
//        val imageMenu: ImageView = findViewById(R.id.image_menu)
//
//        // Set up click listeners for navigation
//        imageGroup3.setOnClickListener {
//            imageGroup3.setImageResource(R.drawable.drawable_image_7)
//            imageGroup1.setImageResource(R.drawable.drawable_image_8)
//            val intent = Intent(this, DoctorMyDoctor::class.java) // Replace with your activity
//            startActivity(intent)
//        }
//
//        imageGroup1.setOnClickListener {
//            imageGroup1.setImageResource(R.drawable.image_group1)
//            imageGroup3.setImageResource(R.drawable.image_group3)
//            val intent = Intent(this, MyFavoriteDoctor::class.java) // Replace with your activity
//            startActivity(intent)
//        }
//
//        imageGroup2.setOnClickListener {
//
//            val intent = Intent(this, DoctorMyDoctor::class.java) // Replace with your activity
//            startActivity(intent)
//        }
//
//        imageMenu.setOnClickListener {
//            val intent = Intent(this, MyProfile::class.java) // Replace with your activity
//            startActivity(intent)
//        }
   // }

}

