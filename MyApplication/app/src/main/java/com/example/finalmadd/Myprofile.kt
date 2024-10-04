package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView


class MyProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_menu_screen)


        val profileImageView: ImageView = findViewById(R.id.container_image_profile)
// Set OnClickListener to navigate to the profile page (new activity)
        profileImageView.setOnClickListener {
            val intent = Intent(this, ProfileViewActivity::class.java)
            startActivity(intent)
        }


        val MedicalImageView: ImageView = findViewById(R.id.img_rectangle_my_medical)
// Set OnClickListener to navigate to the profile page (new activity)
        MedicalImageView.setOnClickListener {
            val intent = Intent(this, MedicalRecords::class.java)
            startActivity(intent)
        }



        val headlineImageViewBack: ImageView = findViewById(R.id.img_close)

        // Set OnClickListener to return to the previous page
        headlineImageViewBack.setOnClickListener {
            val intent = Intent(this, FindDoctorActivity::class.java) // Replace with your activity
            startActivity(intent)
        }

        val headlineImageView: ImageView = findViewById(R.id.img_group_logout)

        // Set OnClickListener to return to the previous page
        headlineImageView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Replace with your activity
            startActivity(intent)
        }


        // Find the img_rectangle_my_doctors ImageView
        val imgRectangleMyDoctors: ImageView = findViewById(R.id.img_rectangle_my_doctors)

        // Set up a click listener for img_rectangle_my_doctors
        imgRectangleMyDoctors.setOnClickListener {
            // Navigate to the new container_find_doctors_screen activity
            val intent = Intent(this, FindDoctorsScreenActivity::class.java)
            startActivity(intent)
        }


    }
}
