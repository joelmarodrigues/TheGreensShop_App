package com.example.thegreensshop_app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        auth = FirebaseAuth.getInstance()

        val usernameEditText: EditText = findViewById(R.id.username_edittext)
        val firstNameEditText: EditText = findViewById(R.id.firstname_edittext)
        val lastNameEditText: EditText = findViewById(R.id.lastname_edittext)
        val emailEditText: EditText = findViewById(R.id.email_edittext)
        val passwordEditText: EditText = findViewById(R.id.password_edittext)
        val addressEditText: EditText = findViewById(R.id.address_edittext)
        val streetEditText: EditText = findViewById(R.id.street_edittext)
        val cityEditText: EditText = findViewById(R.id.city_edittext)
        val zipcodeEditText: EditText = findViewById(R.id.zipcode_edittext)
        val phoneEditText: EditText = findViewById(R.id.phone_edittext)



        val signupButton: Button = findViewById(R.id.signup_button)


        signupButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val user = "$firstName $lastName"
            val address = addressEditText.text.toString()
            val street = streetEditText.text.toString()
            val city = cityEditText.text.toString()
            val zipcode = zipcodeEditText.text.toString()
           // val lat = latEditText.text.toString()
            //val long = longEditText.text.toString()
          //  val geolocation = "$lat,$long"
            val phone = phoneEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()


            // TODO: enviar objeto User para o servidor para registro
        }
    }
}