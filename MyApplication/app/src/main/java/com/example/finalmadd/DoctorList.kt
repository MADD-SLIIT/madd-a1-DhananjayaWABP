package com.example.finalmadd
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.widget.EditText
import android.widget.ImageView


class DoctorList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorAdapter: DoctorAdapter
    private val doctorList = ArrayList<Doctor>()
    private val filteredDoctorList = ArrayList<Doctor>() // For search filtering
    private lateinit var searchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_find_doctors_screen)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        searchBar = findViewById(R.id.edit_text_find_doctor)

        recyclerView.layoutManager = LinearLayoutManager(this)
        doctorAdapter = DoctorAdapter(filteredDoctorList)
        recyclerView.adapter = doctorAdapter




        val image_group_find_doctor: ImageView = findViewById(R.id.image_group_find_doctor)
        // Set OnClickListener to navigate to the profile page (new activity)
        image_group_find_doctor.setOnClickListener {
            val intent = Intent(this, FindDoctorActivity::class.java)
            startActivity(intent)
        }

        // Firebase database reference
        val database = FirebaseDatabase.getInstance().getReference("doctors")

        // Fetch data from Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                doctorList.clear()
                for (doctorSnapshot in snapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctor::class.java)
                    doctor?.let { doctorList.add(it) }
                }
                // Initially, show all doctors
                filteredDoctorList.addAll(doctorList)
                doctorAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors
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
    }

    // Filter function to search through the doctor list based on the input
    private fun filterDoctors(query: String) {
        val searchText = query.lowercase() // Make the search case-insensitive
        filteredDoctorList.clear()

        if (searchText.isEmpty()) {
            // If search is empty, show the full list
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

}
