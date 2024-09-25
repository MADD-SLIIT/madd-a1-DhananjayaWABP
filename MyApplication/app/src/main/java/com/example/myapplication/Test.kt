package com.example.madd_buddi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madd_buddi.databinding.DoctorCardBinding
import com.google.firebase.database.*

class Test : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var binding: DoctorCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DoctorCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Retrieve data from Firebase
        getDoctorData()
    }

    private fun getDoctorData() {
        val doctorReference = database.child("doctors") // Reference to the "doctors" node

        doctorReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear any existing data in the TextViews (if you want to display a list)
                binding.textDrName.text = ""
                binding.textSpecialization.text = ""
                binding.textYearsExperience.text = ""
                binding.textRatings.text = ""
                binding.textPatientStories.text = ""
                binding.textNextAvailable.text = ""
                binding.buttonBookNow.text = ""

                // Iterate through each doctor in the snapshot
                for (doctorSnapshot in dataSnapshot.children) {
                    val doctor = doctorSnapshot.getValue(Doctor::class.java)

                    // Here, you can update the UI or handle the data as needed.
                    // For example, you can display the data in a RecyclerView or concatenate it in the TextViews.

                    binding.textDrName.append("${doctor?.name ?: "No name available"}\n")
                    binding.textSpecialization.append("${doctor?.specialization ?: "No specialization available"}\n")
                    binding.textYearsExperience.append("${doctor?.experience_years ?: 0} Years of Experience\n")
                    binding.textRatings.append("${doctor?.ratings ?: 0}%\n")
                    binding.textPatientStories.append("${doctor?.patient_stories ?: 0} Patient Stories\n")
                    binding.textNextAvailable.append("${doctor?.next_available ?: "Not Available"}\n")
                    binding.buttonBookNow.append("${doctor?.book_button_text ?: "Book Now"}\n")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

}


