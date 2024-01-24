package com.example.mydiary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import android.app.DatePickerDialog
import android.view.View
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat

class LookAllNote : AppCompatActivity() {
    companion object {
        const val LOOK_NOTE_REQUEST_CODE = 1
    }
    //private var selectedDate: String? = null
    var selectedDate: String? = null // инициализация по умолчанию

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var diaryListView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.w_look_all_note)

        dbHelper = DataBaseHelper(this)
        diaryListView = findViewById(R.id.listViewProfiles)


        val diaryEntries = dbHelper.getAllDiaryEntries()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, diaryEntries.map { "${it.date}: ${it.title}" })


        diaryListView.adapter = adapter

        // Добавление обработчика кликов
        diaryListView.setOnItemClickListener { _, _, position, _ ->
            val selectedEntry = diaryEntries[position]
            val entryText = "${selectedEntry.date}: ${selectedEntry.title}"

            // Запуск активности LookNote с передачей данных
            val intent = Intent(this, LookNote::class.java)
            intent.putExtra("diary_entry_id", selectedEntry.id)
            startActivityForResult(intent, LOOK_NOTE_REQUEST_CODE)
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, WriteDiary::class.java)
            startActivity(intent)
        }

        val buttonFilter = findViewById<ImageButton>(R.id.buttonFilter)
        val background = findViewById<LinearLayout>(R.id.linearLayout2)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        // Обработчик изменения даты в CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Обработка выбранной даты
            selectedDate = formatDate(year, month, dayOfMonth)

            // Вызовите функцию фильтрации и обновите список
            updateDiaryList(selectedDate)
        }

        buttonFilter.setOnClickListener {
            // При клике на кнопку "Фильтр"
            background.visibility = View.VISIBLE
        }

        val btnApplyFilter = findViewById<Button>(R.id.btnApplyFilter)
        btnApplyFilter.setOnClickListener {

            updateDiaryList(selectedDate)

            // При клике на кнопку "Применить фильтр"
            background.visibility = View.GONE
        }
    }

    // Функция для форматирования даты
    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        Log.d("LookAllNote", "Formatted date: $formattedDate") // Временное логирование
        return formattedDate
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOOK_NOTE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Обновление списка после удаления
            updateDiaryList(selectedDate)
        }
    }

    private fun updateDiaryList(selectedDate: String?) {
        Log.d("LookAllNote", "Updating list with date: $selectedDate")

        val diaryEntries: List<DiaryData> = if (selectedDate != null) {
            dbHelper.getDiaryEntriesByDate(selectedDate)
        } else {
            dbHelper.getAllDiaryEntries()
        }

        // Проверка инициализации адаптера перед использованием
        if (!::adapter.isInitialized) {
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, diaryEntries.map { "${it.date}: ${it.title}" })
            diaryListView.adapter = adapter
        } else {
            adapter.clear()
            adapter.addAll(diaryEntries.map { "${it.date}: ${it.title}" })
            adapter.notifyDataSetChanged()
        }
        Log.d("LookAllNote", "List updated.")
    }
}