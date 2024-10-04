package com.example.finalmadd

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class MedicalRecordAdd : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_patient_details_screen)

        val backImageView: ImageView = findViewById(R.id.image_headline_back)
// Set OnClickListener to navigate to the profile page (new activity)
        backImageView.setOnClickListener {
            val intent = Intent(this, MedicalRecords::class.java)
            startActivity(intent)
        }

        // Find the views
        val editName: EditText = findViewById(R.id.edit_name)
        val editDate: EditText = findViewById(R.id.edit_date)
        val editMedicineType: EditText = findViewById(R.id.mage_medicin_type)
        val descriptionType: EditText = findViewById(R.id.discription_type)
        val saveButton: View = findViewById(R.id.image_button_recode_save)

        // Set up DatePicker for the edit_date field
        editDate.setOnClickListener {
            showDatePickerDialog(editDate)
        }

        // Set up click listener for the save button
        saveButton.setOnClickListener {
            saveToDatabase(
                editName.text.toString(),
                editDate.text.toString(),
                editMedicineType.text.toString(),
                descriptionType.text.toString()
            )
        }
    }

    // Function to show the date picker dialog
    private fun showDatePickerDialog(editDate: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editDate.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show()
    }

    // Function to save data to Firebase Database
    private fun saveToDatabase(
        name: String,
        date: String,
        medicineType: String,
        description: String
    ) {
        if (name.isEmpty() || date.isEmpty() || medicineType.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Get a reference to the Firebase database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("medical_records")

        // Create a unique ID for each record
        val recordId = myRef.push().key

        // Create a data object to store the medical record
        val medicalRecord = MedicalRecord(name, date, medicineType, description)

        // Save the data under the unique ID
        if (recordId != null) {
            myRef.child(recordId).setValue(medicalRecord)
                .addOnSuccessListener {
                    Toast.makeText(this, "Record saved successfully!", Toast.LENGTH_SHORT).show()
                    // Optionally, clear fields after saving
                    clearFields()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Function to clear input fields
    private fun clearFields() {
        findViewById<EditText>(R.id.edit_name).text.clear()
        findViewById<EditText>(R.id.edit_date).text.clear()
        findViewById<EditText>(R.id.image_medicin_type).text.clear()
        findViewById<EditText>(R.id.discription_type).text.clear()
    }


    data class MedicalRecord(
        val name: String,
        val date: String,
        val medicineType: String,
        val description: String
    )
}