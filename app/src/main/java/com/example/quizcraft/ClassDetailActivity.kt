package com.example.quizcraft

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ClassDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        val className = intent.getStringExtra("CLASS_NAME") ?: return finish()
        findViewById<TextView>(R.id.detailTitle).text = className

        findViewById<Button>(R.id.uploadFilesButton).setOnClickListener {
            // TODO: Now I also have to make file upload here
        }
        findViewById<Button>(R.id.takeQuizButton).setOnClickListener {
            // TODO: Take the user to the quiz page
        }
    }
}
