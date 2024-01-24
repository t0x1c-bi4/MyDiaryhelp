package com.example.mydiary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChangeNote : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w_change_note)

        dbHelper = DataBaseHelper(this)
        editTextTitle = findViewById(R.id.editText)
        editTextContent = findViewById(R.id.textp1)

        val intent = intent
        val entryId = intent.getIntExtra("diary_entry_id", -1)
        if (intent != null) {
            val diaryEntryId = intent.getIntExtra("diary_entry_id", -1)
            val title = intent.getStringExtra("title")
            val content = intent.getStringExtra("content")

            // Заполнение полей EditText
            val editTextTitle = findViewById<EditText>(R.id.editText)
            val editTextContent = findViewById<EditText>(R.id.textp1)

            editTextTitle.setText(title)
            editTextContent.setText(content)
        }

        val buttonSaveChanges = findViewById<Button>(R.id.buttonSaveChange)
        buttonSaveChanges.setOnClickListener {
            // Обработка сохранения изменений
            saveChanges(entryId)
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun saveChanges(entryId: Int) {
        val newTitle = editTextTitle.text.toString()
        val newContent = editTextContent.text.toString()

        // Обновляем запись в базе данных
        val success = dbHelper.updateDiaryEntry(entryId, newTitle, newContent)

        if (success) {
            // Успешное обновление
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show()

            // Оповестите LookAllNote, что данные были изменены (если это необходимо)
            val resultIntent = Intent()
            resultIntent.putExtra("diary_entry_id", entryId)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        } else {
            // Не удалось обновить запись
            Toast.makeText(this, "Не удалось сохранить изменения", Toast.LENGTH_SHORT).show()
        }
    }
}