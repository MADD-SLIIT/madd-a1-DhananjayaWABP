package com.example.finalmadd

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MedicalRecordsList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var medicalRecordsAdapter: MedicalRecordsAdapter
    private val recordsList = mutableListOf<MedicalRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_scroll_main) // Ensure you have this layout

        recyclerView = findViewById(R.id.recyclerView_record)
        recyclerView.layoutManager = LinearLayoutManager(this)


        // Fetch data from Firebase
        val databaseRef = FirebaseDatabase.getInstance().getReference("medical_records")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recordsList.clear()
                for (recordSnapshot in snapshot.children) {
                    val medicalRecord = recordSnapshot.getValue(MedicalRecord::class.java)
                    medicalRecord?.let { recordsList.add(it) }
                }
                medicalRecordsAdapter = MedicalRecordsAdapter(recordsList)
                recyclerView.adapter = medicalRecordsAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MedicalRecordsList, "Failed to load data.", Toast.LENGTH_SHORT).show()
            }
        })
        setupNavigation()
    }

    // Function to set up navigation for image views
    private fun setupNavigation() {

        val imageGroupBack: ImageView = findViewById(R.id.image_group_back)
        val imageGroup3: ImageView = findViewById(R.id.image_group3)
        val imageGroup1: ImageView = findViewById(R.id.image_group1)
        val imageGroup2: ImageView = findViewById(R.id.image_group2)
        val imageMenu: ImageView = findViewById(R.id.image_menu)



        imageGroupBack.setOnClickListener {
            val intent = Intent(this, FindDoctorActivity::class.java) // Replace with your activity
            startActivity(intent)
        }

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
            imageGroup1.setImageResource(R.drawable.drawable_image_8)
            imageGroup3.setImageResource(R.drawable.image_group3)

            val intent = Intent(this, MedicalRecordsList::class.java) // Replace with your activity
            startActivity(intent)
        }

        imageMenu.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java) // Replace with your activity
            startActivity(intent)
        }
    }
}
