package com.example.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WriteDiary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_diary)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonNewNote = findViewById<Button>(R.id.buttonNewNote)
        buttonNewNote.setOnClickListener {
            val intent = Intent(this, NewNote::class.java)
            startActivity(intent)
        }
        val buttonLookNote = findViewById<Button>(R.id.buttonLookNote)
        buttonLookNote.setOnClickListener {
            val intent = Intent(this, LookAllNote::class.java)
            startActivity(intent)
        }
    }
}