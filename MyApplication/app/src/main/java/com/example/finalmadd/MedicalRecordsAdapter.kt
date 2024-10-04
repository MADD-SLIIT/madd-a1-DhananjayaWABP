package com.example.finalmadd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicalRecordsAdapter(private val recordsList: List<MedicalRecord>) :
    RecyclerView.Adapter<MedicalRecordsAdapter.RecordViewHolder>() {

    class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textDate: TextView = itemView.findViewById(R.id.text_date)
        val textMonth: TextView = itemView.findViewById(R.id.text_month)
        val textMedicineType: TextView = itemView.findViewById(R.id.text_record_rabies_vaccination)
        val textPatientName: TextView = itemView.findViewById(R.id.text_records_patient_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medical_record_card, parent, false)
        return RecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = recordsList[position]

        // Parse the date string (dd/mm/yyyy) into components
        record.date?.let {
            val parts = it.split("/")
            if (parts.size == 3) {
                holder.textDate.text = parts[0]  // Date part (dd)
                val month = getMonthName(parts[1].toInt())  // Month part (mm)
                holder.textMonth.text = month
            }
        }

        holder.textMedicineType.text = record.medicineType
        holder.textPatientName.text = record.name
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }

    // Helper function to convert month number to month name
    private fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> ""
        }
    }
}
