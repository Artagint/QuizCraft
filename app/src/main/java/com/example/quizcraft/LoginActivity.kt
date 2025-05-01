package com.example.quizcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // grab all the text filez
        val emailInput     = findViewById<EditText>(R.id.emailInput)
        val passwordInput  = findViewById<EditText>(R.id.passwordInput)
        val gotoQuizButton = findViewById<Button>(R.id.gotoQuizButton)
        val forgotPassword = findViewById<TextView>(R.id.forgotPassword)
        val signUpButton   = findViewById<TextView>(R.id.signOut)
        val auth = FirebaseAuth.getInstance()

        // SIGN UP
        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        gotoQuizButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val pass  = passwordInput.text.toString()

            // Just check all the fields
            var ok = true
            if (email.isEmpty()) {
                emailInput.error = "Email required"
                ok = false
            }
            if (pass.isEmpty()) {
                passwordInput.error = "Password required"
                ok = false
            }
            if (!ok) return@setOnClickListener

            // this is where I call firebase
            auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, MenuActivity::class.java))
                        finish()
                    } else {
                        AlertDialog.Builder(this)
                            .setTitle("Login Failed")
                            .setMessage(task.exception?.message ?: "Unknown error")
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
        }

        // FORGOT PASSWORD
        forgotPassword.setOnClickListener {
            val email = emailInput.text.toString().trim()
            if (email.isEmpty()) {
                emailInput.error = "Enter email to reset password"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    val dlg = AlertDialog.Builder(this)
                    if (task.isSuccessful) {
                        dlg.setTitle("Email Sent")
                            .setMessage("Password reset link sent to $email")
                    } else {
                        dlg.setTitle("Error")
                            .setMessage(task.exception?.message ?: "Could not send reset email")
                    }
                    dlg.setPositiveButton("OK", null)
                        .show()
                }
        }
    }
}
