package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MyFavoriteDoctor : AppCompatActivity() {

    private lateinit var favoriteDoctorsRecyclerView: RecyclerView
    private lateinit var doctorAdapter: FavDoctorAdapter
    private val doctorList = ArrayList<Doctor>()    // Stores only favorite doctors
    private val filteredDoctorList = ArrayList<Doctor>()  // For search filtering
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_doctor_main)

        // Initialize views
        favoriteDoctorsRecyclerView = findViewById(R.id.favoriteDoctorsRecyclerView)

        // Set GridLayoutManager with 2 columns
        val gridLayoutManager = GridLayoutManager(this, 2)  // 2 Columns for a 2x2 grid
        favoriteDoctorsRecyclerView.layoutManager = gridLayoutManager

        searchBar = findViewById(R.id.edit_text_fav_doctor)

        // Initialize the adapter with the filtered list
        doctorAdapter = FavDoctorAdapter(filteredDoctorList)
        favoriteDoctorsRecyclerView.adapter = doctorAdapter

        // Firebase database reference
        val database = FirebaseDatabase.getInstance().getReference("doctors")

        // Fetch data from Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorList.clear()
                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctor::class.java)
                    if (doctor != null) {
                        // Add each doctor to the list
                        doctorList.add(doctor)
                    }
                }

                // Initially show all favorite doctors
                filteredDoctorList.clear()
                filteredDoctorList.addAll(doctorList)
                doctorAdapter.notifyDataSetChanged()

                // Log for debugging purposes
                Log.d("FirebaseData", "Total favorite doctors: ${doctorList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Failed to read data", error.toException())
            }
        })

        // Set up search bar listener
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterDoctors(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        setupNavigation()
    }

    // Filter function to search through the favorite doctor list based on the input
    private fun filterDoctors(query: String) {
        val searchText = query.lowercase() // Make the search case-insensitive
        filteredDoctorList.clear()

        if (searchText.isEmpty()) {
            // If search is empty, show the full list of favorite doctors
            filteredDoctorList.addAll(doctorList)
        } else {
            // Filter by name or specialization
            for (doctor in doctorList) {
                val doctorName = doctor.name?.lowercase() ?: ""
                val doctorSpecialization = doctor.specialization?.lowercase() ?: ""

                if (doctorName.contains(searchText) || doctorSpecialization.contains(searchText)) {
                    filteredDoctorList.add(doctor)
                }
            }
        }

        // Notify the adapter of the changes
        doctorAdapter.notifyDataSetChanged()
    }

    // Function to set up navigation for image views
    private fun setupNavigation() {
        val imageGroup3: ImageView = findViewById(R.id.image_group3)
        val imageGroup1: ImageView = findViewById(R.id.image_group1)
        val imageGroup2: ImageView = findViewById(R.id.image_group2)
        val imageMenu: ImageView = findViewById(R.id.image_menu)

        // Set up click listeners for navigation
        imageGroup3.setOnClickListener {
            val intent = Intent(this, FindDoctorActivity::class.java)
            startActivity(intent)
        }

        imageGroup1.setOnClickListener {
            val intent = Intent(this, MyFavoriteDoctor::class.java)
            startActivity(intent)
        }

        imageGroup2.setOnClickListener {
            val intent = Intent(this, MedicalRecordsList::class.java)
            startActivity(intent)
        }

        imageMenu.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            startActivity(intent)
        }
    }
}
