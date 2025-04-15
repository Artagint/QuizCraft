package com.example.quizcraft
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    lateinit var authenticateUser: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Initialize the Firebase authentication for the user
        authenticateUser = FirebaseAuth.getInstance()

        // Make vals for all of the input fields
        val emailInput = findViewById<EditText>(R.id.emailInput)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val confirmPasswordInput = findViewById<EditText>(R.id.confirmPasswordInput)
        val signupButton = findViewById<Button>(R.id.signupButton)

        // This is where the user can toggle between viewing or not viewing the password field chars
        val passwordToggle = findViewById<ImageView>(R.id.passwordToggle)
        val confirmPasswordToggle = findViewById<ImageView>(R.id.confirmPasswordToggle)

        var isPasswordVisible = false
        var isConfirmPasswordVisible = false

        passwordToggle.setOnClickListener{
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible){
                passwordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(R.drawable.eye_open)
            }
            else{
                passwordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(R.drawable.eye_closed)
            }
            passwordInput.setSelection(passwordInput.text.length)
        }

        confirmPasswordToggle.setOnClickListener{
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible){
                confirmPasswordInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                confirmPasswordToggle.setImageResource(R.drawable.eye_open)
            }
            else{
                confirmPasswordInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirmPasswordToggle.setImageResource(R.drawable.eye_closed)
            }
            confirmPasswordInput.setSelection(confirmPasswordInput.text.length)
        }

        // Take the user to the 'Login' page
        val loginButton = findViewById<TextView>(R.id.gotoLogin)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        /*
        * This is the basic logic behind the sign up process, when the user fills out the fields
        * and clicks the 'Sign Up' button, makes sure the fields are valid (let user know if not),
        * and then make the call to Firebase to add the user to the database
         */
        signupButton.setOnClickListener{
            val email = emailInput.text.toString().trim()
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            /*
            * Make sure that the fields are all filled in, that the email ends in a valid address,
            * that the passwords match, and also that the password pattern is valid, also Firebase
            * automatically checks if an email is already in use and will Toast the user with the
            * proper message
             */
            if(email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length < 8 || !password.any { it.isUpperCase() } || !password.any { it.isLowerCase()} ||
                !password.any {it.isDigit()} ||
                !password.any {"!@#\$%^&*()-_=+[]{}|;:'\",.<>?/`~".contains(it)}){
                Toast.makeText(this, "Password is too weak, please follow the password requirements", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(password != confirmPassword){
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save the user info to Firebase
            authenticateUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Success! Your account has been created", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MenuActivity::class.java))
                    finish()
                }
                else{
                    val error = task.exception?.message?: "Unknown error"
                    Toast.makeText(this, "Sign Up failed: $error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}