package com.example.finalmadd

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class AppointmentActivity : AppCompatActivity() {

    // Firebase reference
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doctor_appointment_screen_01) // Reference the layout file

        // Retrieve the doctor details from the Intent
        val doctorId = intent.getStringExtra("doctor_id")
        val doctorName = intent.getStringExtra("doctor_name")
        val doctorSpecialization = intent.getStringExtra("doctor_specialization")

        // Set the doctor name and specialization in TextViews
        findViewById<TextView>(R.id.Appoiment_doctor_name).text = doctorName
        findViewById<TextView>(R.id.Doctor_specialist).text = doctorSpecialization

        // Date and Time input fields
        val dateInput: EditText = findViewById(R.id.date_input)
        val timeInput: EditText = findViewById(R.id.time_input)

        // Additional input fields (Pet Owner Name, Contact Number, Address)
        val petOwnerNameInput: EditText = findViewById(R.id.pet_owner_name)
        val contactNumberInput: EditText = findViewById(R.id.contact_number)
        val addressInput: EditText = findViewById(R.id.address)

        // Firebase Database Reference
        database = FirebaseDatabase.getInstance().reference

        // Set up date picker when date input is clicked
        dateInput.setOnClickListener {
            showDatePicker(dateInput)
        }

        // Set up time picker when time input is clicked
        timeInput.setOnClickListener {
            showTimePicker(timeInput)
        }

        // Find the ImageView for the confirm button
        val buttonConfirm: ImageView = findViewById(R.id.button_confirm)

        // Set the click listener to save appointment details to Firebase
        buttonConfirm.setOnClickListener {
            val date = dateInput.text.toString()
            val time = timeInput.text.toString()
            val petOwnerName = petOwnerNameInput.text.toString()
            val contactNumber = contactNumberInput.text.toString()
            val address = addressInput.text.toString()

            // Ensure all fields are filled
            if (doctorId.isNullOrEmpty() || petOwnerName.isEmpty() || contactNumber.isEmpty() || address.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Call the function to save the appointment details
                saveAppointmentToDatabase(doctorId, doctorName ?: "", doctorSpecialization ?: "", petOwnerName, contactNumber, address, date, time)
            }
        }


        val Appoiment_back_navigate: ImageView = findViewById(R.id.Appoiment_back_navigate)
        // Set OnClickListener to navigate to the profile page (new activity)
        Appoiment_back_navigate.setOnClickListener {
            val intent = Intent(this, DoctorDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    // Show the DatePickerDialog and set the selected date in the EditText
    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = "$selectedDay/${selectedMonth + 1}/$selectedYear" // Format the date as dd/MM/yyyy
            editText.setText(date)
        }, year, month, day)

        datePickerDialog.show()
    }

    // Show the TimePickerDialog and set the selected time in the EditText
    private fun showTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val time = String.format("%02d:%02d", selectedHour, selectedMinute) // Format the time as HH:mm
            editText.setText(time)
        }, hour, minute, true) // 'true' for 24-hour format

        timePickerDialog.show()
    }

    // Function to save the appointment details to Firebase Realtime Database
    private fun saveAppointmentToDatabase(
        doctorId: String,
        doctorName: String,
        doctorSpecialization: String,
        petOwnerName: String,
        contactNumber: String,
        address: String,
        date: String,
        time: String
    ) {
        // Log to check if we are reaching this point
        Log.d("Firebase", "Saving appointment...")

        // Create a unique ID for each appointment
        val appointmentId = database.child("appointments").push().key
        Log.d("Firebase", "Generated Appointment ID: $appointmentId");

        // Create a map to store the appointment data
        val appointmentDetails = mapOf(
            "doctorId" to doctorId,
            "doctorName" to doctorName,
            "doctorSpecialization" to doctorSpecialization,
            "petOwnerName" to petOwnerName,
            "contactNumber" to contactNumber,
            "address" to address,
            "date" to date,
            "time" to time
        )

        // Save the data to Firebase under the unique appointment ID
        if (appointmentId != null) {
            database.child("appointments").child(appointmentId).setValue(appointmentDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "Appointment saved successfully to 'appointments' path")
                        Toast.makeText(this, "Appointment booked successfully", Toast.LENGTH_SHORT).show()
                        showThankYouPopup()
                    } else {
                        // Log the exact error to understand why it's failing
                        Log.e("Firebase", "Failed to book appointment: ${task.exception?.message}")
                        Toast.makeText(this, "Failed to book appointment", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Log.e("Firebase", "Failed to generate appointment ID")
            Toast.makeText(this, "Failed to generate appointment ID", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to display the Thank You pop-up
    // Function to display the Thank You pop-up
    private fun showThankYouPopup() {
        // Log message to check if method is being called
        Log.d("AppointmentActivity", "showThankYouPopup called")

        // Inflate the dialog view
        val dialogView = layoutInflater.inflate(R.layout.thankyou_alert, null)

        if (dialogView == null) {
            Log.e("AppointmentActivity", "Dialog view inflation failed")
            return
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Find the ImageView and TextView in the dialog layout
        val doneButton: ImageView = dialogView.findViewById(R.id.image_thank_you_rectangle2)

        if (doneButton == null) {
            Log.e("AppointmentActivity", "Done button not found in the dialog")
            return
        }

        // Log message to check if button click listener is being set
        Log.d("AppointmentActivity", "Setting click listener on done button")

        // Set an OnClickListener on the done button to go to another activity
        doneButton.setOnClickListener {
            // Go to another activity (e.g., DoctorList)
            val intent = Intent(this, DoctorList::class.java)
            startActivity(intent)
            dialog.dismiss() // Close the dialog after navigation
        }

        dialog.show()
    }


}
