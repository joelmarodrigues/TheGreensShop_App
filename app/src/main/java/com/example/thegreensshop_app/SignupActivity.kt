package com.example.thegreensshop_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
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

            //Handling exceptions
            // Check if any field is empty
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.flatHouseNo.isEmpty() || address.street.isEmpty() || address.city.isEmpty() || address.zipcode.isEmpty()) {
                Snackbar.make(signupButton, "Please fill out all fields.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if email is valid
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Snackbar.make(signupButton, "Please enter a valid email address.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if password meets requirements
            if (password.length < 8 || !password.matches(Regex(".*\\d.*[a-zA-Z].*"))){
                Snackbar.make(signupButton, "Password must contain at least 8 characters with numbers and letters.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if phone number is valid
            if (phone.length < 8 || phone.length > 10 || !TextUtils.isDigitsOnly(phone)) {
                Snackbar.make(signupButton, "Please enter a valid phone number (8 to 10 digits).", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Random geolocation
            val random = Random()
            val minLat = -90.0
            val maxLat = 90.0
            val minLong = -180.0
            val maxLong = 180.0
            val lat = minLat + (maxLat - minLat) * random.nextDouble()
            val long = minLong + (maxLong - minLong) * random.nextDouble()
            val geolocation = "$lat,$long"

            val newUser = User(name, email, phone, address, geolocation)

            // Get Firestore database reference for the user collection
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(email)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Email already registered, show error message
                        Snackbar.make(signupButton, "This email is already registered. Please use a different email.", Snackbar.LENGTH_SHORT).show()
                    } else {

                        // Email not registered, create new user
                        userRef.set(newUser)
                            .addOnSuccessListener {
                                // User created in the database, show success message
                                Log.d(TAG, "Account created!")
                                Toast.makeText(this, "Your account has been created successfully!", Toast.LENGTH_SHORT).show()

                                // Sign up user with Firebase Authentication
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(this) { task ->
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "User signed up with Firebase Authentication successfully!")
                                            Toast.makeText(this, "You are now signed up and logged in!", Toast.LENGTH_SHORT).show()

                                            // Check if user was added to Firebase Authentication successfully
                                            val user = auth.currentUser
                                            if (user != null) {
                                                val intent = Intent(this, MainActivity::class.java)
                                                startActivity(intent)
                                            } else {
                                                Log.w(TAG, "Error signing up user with Firebase Authentication", task.exception)
                                                Toast.makeText(this, "Error signing up user. Please try again later.", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            Log.w(TAG, "Error signing up user with Firebase Authentication", task.exception)
                                            Toast.makeText(this, "Error signing up user. Please try again later.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error creating user", e)
                                Toast.makeText(this, "Ops! Something went wrong. Please try again later.", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
        }
    }

    data class Name(val firstName: String, val lastName: String)
    data class Address(val flatHouseNo: String, val street: String, val city: String, val zipcode: String, val country: String)
    data class User(val name: Name, val email: String, val phone: String, val address: Address, val geolocation: String)
}

