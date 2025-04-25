package com.example.quizcraft

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.PopupMenu
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val profileImage = findViewById<ImageView>(R.id.profileImage)
        profileImage.setOnClickListener{anchor->
            PopupMenu(this, anchor).apply{
                menuInflater.inflate(R.menu.profile_menu, menu)
                setOnMenuItemClickListener{item->
                    when(item.itemId){
                        R.id.menu_settings->{
                            // TODO: Settings
                            true
                        }
                        R.id.menu_sign_out->{
                            FirebaseAuth.getInstance().signOut()
                            val intent = Intent(this@MenuActivity, LoginActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            startActivity(intent)
                            true
                        }
                        else->false
                    }
                }
                show()
            }
        }
        findViewById<Button>(R.id.gotoQuizButton).setOnClickListener {
            startActivity(Intent(this, GoToClassesActivity::class.java))
        }
        findViewById<Button>(R.id.uploadQuizButton).setOnClickListener {
            startActivity(Intent(this, MakeClassActivity::class.java))
        }
    }
}
