package com.example.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.ContentValues
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class NewNote : AppCompatActivity() {
    private lateinit var  dbHelper: DataBaseHelper
    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w_new_note)

        dbHelper = DataBaseHelper(this)
        dateTextView = findViewById(R.id.textDate)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val title = findViewById<EditText>(R.id.editText).text.toString()
            val content = findViewById<EditText>(R.id.textp1).text.toString()

            // Отображение текущей даты в TextView
            val currentDate = getCurrentDate()
            dateTextView.text = currentDate

            val result = dbHelper.insertDiaryEntry(title, content)

            if (result != -1L) {
                // Запись успешно добавлена в дневник
                Toast.makeText(this, "Дневник сохранен!", Toast.LENGTH_SHORT).show()
            } else {
                // Произошла ошибка при добавлении записи в дневник
                Toast.makeText(this, "Ошибка при сохранении дневника", Toast.LENGTH_SHORT).show()
            }
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, WriteDiary::class.java)
            startActivity(intent)
        }
    }
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", java.util.Locale.getDefault())
        val currentDate = Date(System.currentTimeMillis())
        return dateFormat.format(currentDate)
    }
}