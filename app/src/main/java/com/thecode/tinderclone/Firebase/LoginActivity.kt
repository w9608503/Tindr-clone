package com.thecode.tinderclone.Firebase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.thecode.tinderclone.R
import com.thecode.tinderclone.activities.MainActivity

class LoginActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private val emailEditText: EditText? = null
    private val passwordEditText: EditText? = null
    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginin)
        // Initialize Firebase

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Get the instance of the Firebase App
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if the user is already authenticated
        if (firebaseAuth!!.currentUser != null) {
            // User is already signed in, redirect to the main part of your app
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish() // Finish the SignUpActivity
        }

        // Login Button Click Listener
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginEmailEditText = findViewById(R.id.loginEmailEditText)
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText)
        loginButton.setOnClickListener {
            val loginEmail = loginEmailEditText.getText().toString().trim { it <= ' ' }
            val loginPassword = loginPasswordEditText.getText().toString().trim { it <= ' ' }
            firebaseAuth!!.signInWithEmailAndPassword(loginEmail, loginPassword)
                .addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI or navigate to the next activity
                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT)
                            .show()
                        val i = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@LoginActivity, "Login failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    fun signup(view: View?) {
        val i = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(i)
    }
}