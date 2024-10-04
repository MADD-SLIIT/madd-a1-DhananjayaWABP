package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MedicalRecords : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_medical_records_screen)

        val AddRecordImageView: ImageView = findViewById(R.id.image_button_add_recoreds)
// Set OnClickListener to navigate to the profile page (new activity)
        AddRecordImageView.setOnClickListener {
            val intent = Intent(this, MedicalRecordAdd::class.java)
            startActivity(intent)
        }

        val backImageView: ImageView = findViewById(R.id.back_code_add)
// Set OnClickListener to navigate to the profile page (new activity)
        backImageView.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }


    }
}
