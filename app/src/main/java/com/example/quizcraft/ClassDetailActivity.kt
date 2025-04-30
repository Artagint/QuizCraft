package com.example.quizcraft

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClassDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        // Grab the class name passed in and show it
        val className = intent.getStringExtra("CLASS_NAME") ?: return finish()
        findViewById<TextView>(R.id.detailTitle).text = className

        // Two stub buttons—no functionality yet
        findViewById<Button>(R.id.uploadFilesButton).setOnClickListener {
            // TODO: wire up upload-files flow
        }
        findViewById<Button>(R.id.takeQuizButton).setOnClickListener {
            // TODO: wire up take-quiz flow
        }
    }
}
