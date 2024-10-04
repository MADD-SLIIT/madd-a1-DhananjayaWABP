package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FindDoctorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_find_doctor)

        // Handle find_doctor_button click to navigate to another page
        val findDoctorButton: FrameLayout = findViewById(R.id.find_doctor_button)
        findDoctorButton.setOnClickListener {
            // Navigate to the DoctorList activity
            val intent = Intent(this, DoctorList::class.java)
            startActivity(intent)
        }

        setupNavigation()
    }


    // Function to set up navigation for image views
    private fun setupNavigation() {
        val imageGroup3: ImageView = findViewById(R.id.image_group3)
        val imageGroup1: ImageView = findViewById(R.id.image_group1)
        val imageGroup2: ImageView = findViewById(R.id.image_group2)
        val imageMenu: ImageView = findViewById(R.id.image_menu)

        // Set up click listeners for navigation
        imageGroup3.setOnClickListener {
            imageGroup3.setImageResource(R.drawable.drawable_image_7)
            imageGroup1.setImageResource(R.drawable.drawable_image_8)
            val intent = Intent(this, FindDoctorActivity::class.java) // Replace with your activity
            startActivity(intent)
        }

        imageGroup1.setOnClickListener {
            imageGroup1.setImageResource(R.drawable.image_group1)
            imageGroup3.setImageResource(R.drawable.image_group3)
            val intent = Intent(this, MyFavoriteDoctor::class.java) // Replace with your activity
            startActivity(intent)
        }

        imageGroup2.setOnClickListener {

            val intent = Intent(this, MedicalRecordsList::class.java) // Replace with your activity
            startActivity(intent)
        }

        imageMenu.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java) // Replace with your activity
            startActivity(intent)
        }
    }
}
