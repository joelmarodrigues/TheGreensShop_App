package com.example.thegreensshop_app.models

data class Address(
    val flatHouseNo: String?,
    val street: String?,
    val city: String?,
    val country: String?,
    val zipcode: String?
) {
    fun get(key: String): String? {
        return when (key) {
            "flatHouseNo" -> flatHouseNo
            "street" -> street
            "city" -> city
            "country" -> country
            "zipcode" -> zipcode
            else -> null
        }
    }
}
