package com.example.finalmadd

data class Doctor(
    val id: String = "", // Default value for id
    var book_button_text: String? = null,
    var experience_years: Int? = null,
    var name: String? = null,
    var next_available: String? = null,
    var patient_stories: Int? = null,
    var ratings: Int? = null,
    var specialization: String? = null,
    var isFavorite: Boolean? = true, // Default to false
    var imageUrl: String? = null, // New field for the image resource name
)

