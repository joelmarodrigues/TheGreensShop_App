package com.example.thegreensshop_app.activities

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
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(user!!.uid)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    // DocumentSnapshot contains data of the user
                    val userObj = document.toObject(User::class.java)

                    // Update UI with user details
                    val name = userObj?.name
                    val email = userObj?.email
                    val phone = userObj?.phone
                    val address = userObj?.address

                    nameTextView.text = name
                    emailTextView.text = email
                    phoneTextView.text = phone
                    addressTextView.text = address

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}