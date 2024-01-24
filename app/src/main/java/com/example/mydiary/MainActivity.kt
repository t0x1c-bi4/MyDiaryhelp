package com.example.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.mydiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonFormsOfFriends = findViewById<Button>(R.id.buttonFormsOfFriends)
        val buttonWriteDiary = findViewById<Button>(R.id.buttonWriteDiary)
        val buttonDreamBook = findViewById<Button>(R.id.buttonDreamBook)
        val buttonMyHealth = findViewById<Button>(R.id.buttonMyHealth)

        fun navigateToActivity(targetActivity: Class<*>) {
            val intent = Intent(this, targetActivity)
            startActivity(intent)
        }

        buttonFormsOfFriends.setOnClickListener { navigateToActivity(FormsOfFriends::class.java) }

        buttonWriteDiary.setOnClickListener { navigateToActivity(WriteDiary::class.java) }

        buttonDreamBook.setOnClickListener { navigateToActivity(DreamBook::class.java) }

        buttonMyHealth.setOnClickListener { navigateToActivity(MyHealth::class.java) }
    }
}