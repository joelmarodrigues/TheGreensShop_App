package com.example.thegreensshop_app.activities

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.thegreensshop_app.R
import com.example.thegreensshop_app.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        mAuth = FirebaseAuth.getInstance()

        nameTextView = findViewById(R.id.name_textview)
        emailTextView = findViewById(R.id.email_textview)
        phoneTextView = findViewById(R.id.phone_textview)
        addressTextView = findViewById(R.id.address_textview)

        val logoutButton = findViewById<Button>(R.id.logout_button)
        logoutButton.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {

            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users").document(user.uid)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // DocumentSnapshot contains data of the user
                        val userObj = document.toObject(User::class.java)

                        // Update UI with user details
                        val address = userObj?.address
                        val city = address?.city
                        val country = address?.country
                        val flatHouseNo = address?.flatHouseNo
                        val street = address?.street
                        val zipcode = address?.zipcode

                        val name = userObj?.name
                        val firstname = name?.firstName
                        val lastname = name?.lastName

                        nameTextView.text = "$firstname $lastname"
                        emailTextView.text = userObj?.email
                        phoneTextView.text = userObj?.phone
                        addressTextView.text = "$flatHouseNo, $street, $city, $country $zipcode"
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
    }
}