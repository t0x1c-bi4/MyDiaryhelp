package com.example.mydiary

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MyHealth : AppCompatActivity() {
    private lateinit var  dbHelper: DataBaseHelper
    private lateinit var healthListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_health)

        dbHelper = DataBaseHelper(this)
        healthListView = findViewById(R.id.listViewProfiles)

        // Инициализация адаптера и установка его в ListView
        val healthEntries = dbHelper.getAllHealthEntries()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, healthEntries)
        healthListView.adapter = adapter

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val date = findViewById<EditText>(R.id.editDate).text.toString()
            val water = findViewById<EditText>(R.id.editGlass).text.toString()

            val result = dbHelper.insertHealthEntry(date, water)

            if (result != -1L) {
                // Запись успешно добавлена в дневник
                Toast.makeText(this, "Запись сохранена!", Toast.LENGTH_SHORT).show()
            } else {
                // Произошла ошибка при добавлении записи в дневник
                Toast.makeText(this, "Ошибка при сохранении записи", Toast.LENGTH_SHORT).show()
            }
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}