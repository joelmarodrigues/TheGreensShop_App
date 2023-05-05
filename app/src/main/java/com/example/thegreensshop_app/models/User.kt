package com.example.thegreensshop_app.models

data class User(
    val name: Name,
    val email: String,
    val phone: String,
    val address: Address,
    val geolocation: String
)
