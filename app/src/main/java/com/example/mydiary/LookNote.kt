package com.example.mydiary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LookNote : AppCompatActivity() {
    companion object {
        const val CHANGE_NOTE_REQUEST_CODE = 1
    }

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var textViewTitle: TextView
    private lateinit var textViewContent: TextView
    private lateinit var profileId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w_look_note)

        dbHelper = DataBaseHelper(this)
        textViewTitle = findViewById(R.id.Text)
        textViewContent = findViewById(R.id.textp1)

        // Получение переданных данных из Intent
        val diaryEntryId = intent.getIntExtra("diary_entry_id", -1)

        // Загрузка данных из базы данных по id
        val entry = dbHelper.getDiaryEntryById(diaryEntryId.toString())

        // Инициализация profileId
        profileId = entry?.id.toString()

        // Отображение данных в текстовых полях
        textViewTitle.text = entry?.title
        textViewContent.text = entry?.content

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, LookAllNote::class.java)
            startActivity(intent)
        }
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            onDeleteButtonClick(diaryEntryId)
        }

        val buttonChange = findViewById<Button>(R.id.buttonChange)
        buttonChange.setOnClickListener {
            val intent = Intent(this, ChangeNote::class.java)

            // Получение данных текущей записи
            val currentEntry = dbHelper.getDiaryEntryById(profileId)

            // Передача данных в ChangeNote
            if (currentEntry != null) {
                intent.putExtra("diary_entry_id", currentEntry.id)
                intent.putExtra("title", currentEntry.title)
                intent.putExtra("content", currentEntry.content)
            }

            startActivityForResult(intent, CHANGE_NOTE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHANGE_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedDiaryEntryId = data?.getIntExtra("diary_entry_id", -1)
            val updatedTitle = data?.getStringExtra("updated_title")
            val updatedContent = data?.getStringExtra("updated_content")

            // Обновление данных в UI, например:
            textViewTitle.text = updatedTitle
            textViewContent.text = updatedContent
        }
    }

    // Обработчик нажатия на кнопку удаления
    fun onDeleteButtonClick(diaryEntryId: Int) {
        // Удаление записи из базы данных
        val isDeleted = dbHelper.deleteDiaryEntry(diaryEntryId)

        if (isDeleted) {
            // Если запись успешно удалена, можно закрыть активность
            Toast.makeText(this, "Запись удалена", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            // Если произошла ошибка при удалении, выведите сообщение об ошибке
            Toast.makeText(this, "Ошибка при удалении записи", Toast.LENGTH_SHORT).show()
        }
    }
}