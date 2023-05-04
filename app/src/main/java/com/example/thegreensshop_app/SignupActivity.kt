package com.example.thegreensshop_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        auth = FirebaseAuth.getInstance()

        val flatHouseNoEditText: EditText = findViewById(R.id.flat_houseNo_edittext)
        val firstNameEditText: EditText = findViewById(R.id.firstname_edittext)
        val lastNameEditText: EditText = findViewById(R.id.lastname_edittext)
        val emailEditText: EditText = findViewById(R.id.email_edittext)
        val passwordEditText: EditText = findViewById(R.id.password_edittext)
        val streetEditText: EditText = findViewById(R.id.street_edittext)
        val cityEditText: EditText = findViewById(R.id.city_edittext)
        val zipcodeEditText: EditText = findViewById(R.id.zipcode_edittext)
        val phoneEditText: EditText = findViewById(R.id.phone_edittext)

        val signupButton: Button = findViewById(R.id.signup_button)

        signupButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val name = Name(firstName, lastName)
            val address = Address(flatHouseNoEditText.text.toString(), streetEditText.text.toString(), cityEditText.text.toString(), zipcodeEditText.text.toString(), "Ireland")
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Generate random geolocation
            val random = Random()
            val minLat = -90.0
            val maxLat = 90.0
            val minLong = -180.0
            val maxLong = 180.0
            val lat = minLat + (maxLat - minLat) * random.nextDouble()
            val long = minLong + (maxLong - minLong) * random.nextDouble()
            val geolocation = "$lat,$long"

            val newUser = User(name, email, phone, address, geolocation)

            // Save user object to Firestore
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(email).set(newUser)
                .addOnSuccessListener {
                    Log.d(TAG, "User created successfully!")
                    Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show()

                    // Sign up user with Firebase Authentication
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User signed up with Firebase Authentication successfully!")
                                Toast.makeText(this, "User signed up with Firebase Authentication successfully!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Log.w(TAG, "Error signing up user with Firebase Authentication", task.exception)
                                Toast.makeText(this, "Error signing up user with Firebase Authentication", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error creating user", e)
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
                }
        }
    }

    data class Name(val firstName: String, val lastName: String)
    data class Address(val flatHouseNo: String, val street: String, val city: String, val zipcode: String, val country: String)
    data class User(val name: Name, val email: String, val phone: String, val address: Address, val geolocation: String)
}

