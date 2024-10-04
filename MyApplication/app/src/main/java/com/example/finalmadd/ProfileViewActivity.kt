package com.example.finalmadd

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProfileViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_view) // Assuming the layout is named profile_view.xml

        // Find the ImageView for the headline
        val headlineImageView: ImageView = findViewById(R.id.image_headline_group)

        // Set OnClickListener to return to the previous page
        headlineImageView.setOnClickListener {
            finish() // Ends the current activity and goes back to the previous one
        }
    }
}
