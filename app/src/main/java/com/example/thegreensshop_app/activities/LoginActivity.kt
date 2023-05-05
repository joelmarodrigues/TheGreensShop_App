package com.example.thegreensshop_app.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thegreensshop_app.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.email_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        val loginButton = findViewById<Button>(R.id.login_button)
        val signupButton = findViewById<Button>(R.id.signup_button)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                emailEditText.error = "Email or Password is empty"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                val token = task2.result?.token
                                // Save token in SharedPreferences
                                val sharedPrefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
                                sharedPrefs.edit().putString("auth_token", token).apply()
                            }
                        }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        when {
                            task.exception?.message?.contains("password") == true -> {
                                Toast.makeText(
                                    baseContext, "Incorrect password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            task.exception?.message?.contains("no user") == true -> {
                                Toast.makeText(
                                    baseContext, "User not found.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else -> {
                                Toast.makeText(
                                    baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        val sharedPrefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        val authToken = sharedPrefs.getString("auth_token", null)

        if (authToken != null) {
            FirebaseAuth.getInstance().signInWithCustomToken(authToken)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Redirect MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Clean token
                        sharedPrefs.edit().remove("auth_token").apply()
                    }
                }
        } else {
            mAuth.currentUser?.let {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "com.example.thegreensshop_app.activities.LoginActivity"
    }
}
