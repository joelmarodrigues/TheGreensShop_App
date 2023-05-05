package com.example.thegreensshop_app.models

data class Name(
    val firstName: String,
    val lastName: String
    ) {
    fun get(key: String): String? {

        return when (key) {
            "firstname" -> firstName
            "lastname" -> lastName
            else -> null
        }
    }
}