package com.example.quizcraft

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GoToClassesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_to_classes)

        val container = findViewById<LinearLayout>(R.id.classes_container)
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("classes")
            .addSnapshotListener { snaps, error ->
                if (error != null) {
                    AlertDialog.Builder(this)
                        .setTitle("Error loading classes")
                        .setMessage(error.message)
                        .setPositiveButton("OK", null)
                        .show()
                    return@addSnapshotListener
                }

                container.removeAllViews()

                snaps?.documents?.forEach { doc ->
                    val className = doc.id
                    val btn = Button(this).apply {
                        text = className
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { topMargin = 16 }
                        setBackgroundColor(Color.parseColor("#A60F2D"))
                        setTextColor(Color.WHITE)
                        textSize = 18f

                        setOnClickListener {
                            // This is what actually launches the detail screen
                            val intent = Intent(
                                this@GoToClassesActivity,
                                ClassDetailActivity::class.java
                            ).putExtra("CLASS_NAME", className)
                            startActivity(intent)
                        }
                    }
                    container.addView(btn)
                }

                if (snaps?.isEmpty == true) {
                    TextView(this).apply {
                        text = "No classes found."
                        setTextColor(Color.LTGRAY)
                        textSize = 16f
                        layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { topMargin = 16 }
                    }.also(container::addView)
                }
            }
    }
}
