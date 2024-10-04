package com.example.finalmadd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavDoctorAdapter(private val doctorList: List<Doctor>) : RecyclerView.Adapter<FavDoctorAdapter.FavDoctorViewHolder>() {

    class FavDoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.text_dr_Name)
        val specializationTextView: TextView = itemView.findViewById(R.id.text_specialist_vet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavDoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_type_2, parent, false)
        return FavDoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavDoctorViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.nameTextView.text = doctor.name
        holder.specializationTextView.text = doctor.specialization
    }

    override fun getItemCount() = doctorList.size
}
