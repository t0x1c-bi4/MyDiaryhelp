package com.example.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FormsOfFriends : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forms_of_friends)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val buttonNewForm = findViewById<Button>(R.id.buttonNewForm)
        buttonNewForm.setOnClickListener {
            val intent = Intent(this, CreateNewForm::class.java)
            startActivity(intent)
        }
        val buttonLookForms = findViewById<Button>(R.id.buttonLookForms)
        buttonLookForms.setOnClickListener {
            val intent = Intent(this, LookAllForms::class.java)
            startActivity(intent)
        }
    }
}