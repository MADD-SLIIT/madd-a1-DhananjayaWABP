package com.example.finalmadd

import android.content.Intent
import android.util.Log  // Add this import
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorAdapter(private val doctorList: List<Doctor>) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    // ViewHolder class to hold references to views in each item
    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val specializationTextView: TextView = itemView.findViewById(R.id.specializationTextView)
        val experienceTextView: TextView = itemView.findViewById(R.id.experienceTextView)
        val ratingsTextView: TextView = itemView.findViewById(R.id.ratingsTextView)
        val patientStoriesTextView: TextView = itemView.findViewById(R.id.patientStoriesTextView)
        val nextAvailableTextView: TextView = itemView.findViewById(R.id.nextAvailableTextView)
        val bookButtonTextView: View = itemView.findViewById(R.id.container_button3)
    }

    // Inflate the item layout and create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    // Bind data to the views of the item
    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]

        // Set doctor details to respective TextViews
        holder.nameTextView.text = doctor.name
        holder.specializationTextView.text = doctor.specialization
        holder.experienceTextView.text = "Experience: ${doctor.experience_years} years"
        holder.ratingsTextView.text = "Ratings: ${doctor.ratings}"
        holder.patientStoriesTextView.text = "Patient Stories: ${doctor.patient_stories}"
        holder.nextAvailableTextView.text = "Next Available: ${doctor.next_available}"

        // Log to confirm doctor id
        Log.d("DoctorAdapter", "Doctor ID: ${doctor.id}")  // Log doctor ID for debugging

        // Set click listener on the "Book" button to navigate to DoctorDetailsActivity
        holder.bookButtonTextView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DoctorDetailsActivity::class.java).apply {
                putExtra("doctor_id", doctor.id)  // Pass unique doctor ID
                putExtra("doctor_name", doctor.name)  // Pass doctor's name
                putExtra("doctor_specialization", doctor.specialization)
                putExtra("doctor_experience", doctor.experience_years)
                putExtra("doctor_ratings", doctor.ratings)
                putExtra("doctor_patient_stories", doctor.patient_stories)
                putExtra("doctor_next_available", doctor.next_available)
            }
            context.startActivity(intent)
        }
    }

    // Return the total number of items in the list
    override fun getItemCount() = doctorList.size
}
