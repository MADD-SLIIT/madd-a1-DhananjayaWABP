package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DoctorDetailsActivity : AppCompatActivity() {

    private var isFavorite = false
    private lateinit var database: DatabaseReference
    private lateinit var doctorId: String  // Assume each doctor has a unique ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_doctor)

        // Retrieve the data from the Intent
        val doctorName = intent.getStringExtra("doctor_name")
        val doctorSpecialization = intent.getStringExtra("doctor_specialization")

        doctorId = intent.getStringExtra("doctor_id") ?: ""  // Initialize doctorId from intent

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("doctors")

        // Find the TextViews and set the doctor details
        findViewById<TextView>(R.id.text_dr_pediatrician_name).text = doctorName
        findViewById<TextView>(R.id.text_dr_pediatrician_specialist).text = doctorSpecialization

        val favoriteButton = findViewById<ImageView>(R.id.image_dr_pediatrician_like)

        // Fetch isFavorite status from Firebase and set the initial state of the favorite button
        fetchFavoriteStatusAndSetIcon(favoriteButton)

        // Handle favorite button click
        favoriteButton.setOnClickListener {
            // Toggle the isFavorite state
            isFavorite = !isFavorite

            // Update the icon and perform actions
            updateFavoriteIcon(favoriteButton)

            // Update database or perform necessary action here
            updateFavoriteStatusInDatabase(isFavorite)
        }

        findViewById<ImageView>(R.id.image_dr_pediatrician_button_rectangle).setOnClickListener {
            navigateToAppointmentActivity(doctorId, doctorName ,doctorSpecialization)
        }


        val image_headline_DoctorDitailback: ImageView = findViewById(R.id.image_headline_DoctorDitailback)
        // Set OnClickListener to navigate to the profile page (new activity)
        image_headline_DoctorDitailback.setOnClickListener {
            val intent = Intent(this, DoctorList::class.java)
            startActivity(intent)
        }


    }

    // Function to fetch the current favorite status from Firebase and update the icon
    private fun fetchFavoriteStatusAndSetIcon(favoriteButton: ImageView) {
        database.child(doctorId).child("isFavorite").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isFavorite = snapshot.getValue(Boolean::class.java) ?: false
                // Update the favorite button based on the fetched status
                updateFavoriteIcon(favoriteButton)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DoctorDetailsActivity", "Failed to fetch favorite status: ${error.message}")
            }
        })
    }

    // Function to update the favorite icon based on the state
    private fun updateFavoriteIcon(favoriteButton: ImageView) {
        if (isFavorite) {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_like1))
        } else {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_like))
        }
    }

    // Function to update favorite status in the Firebase database
    private fun updateFavoriteStatusInDatabase(isFavorite: Boolean) {
        // Check if doctorId has been initialized properly
        if (doctorId.isNotEmpty()) {
            // Create a map to update only the 'isFavorite' field
            val updates = mapOf<String, Any>("isFavorite" to isFavorite)

            // Log the update attempt
            Log.d("DoctorDetailsActivity", "Updating doctor with ID: $doctorId with favorite status: $isFavorite")

            // Update the doctor's favorite status in the Firebase database
            database.child(doctorId).updateChildren(updates)
                .addOnSuccessListener {
                    Log.d("DoctorDetailsActivity", "Doctor favorite status updated successfully in Firebase.")
                }
                .addOnFailureListener { exception ->
                    Log.e("DoctorDetailsActivity", "Failed to update favorite status: ${exception.message}")
                }
        } else {
            Log.e("DoctorDetailsActivity", "Doctor ID is missing, cannot update favorite status.")
        }
    }

    private fun navigateToAppointmentActivity(doctorId: String, doctorName: String?, doctorSpecialization: String?) {
        val intent = Intent(this, AppointmentActivity::class.java)
        intent.putExtra("doctor_id", doctorId)  // Pass doctorId to AppointmentActivity
        intent.putExtra("doctor_name", doctorName)  // Pass doctorName to AppointmentActivity
        intent.putExtra("doctor_specialization",doctorSpecialization )
        startActivity(intent)
    }
}
