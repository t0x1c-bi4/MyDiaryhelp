package com.example.mydiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CreateNewForm : AppCompatActivity() {
    private lateinit var  dbHelper: DataBaseHelper
    private val formData = HashMap<String, String>() // Для хранения данных из полей

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.f_create_new_form)

        dbHelper = DataBaseHelper(this)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            formData["name"] = findViewById<EditText>(R.id.editName).text.toString()
            formData["surname"] = findViewById<EditText>(R.id.editSurname).text.toString()
            formData["nickname"] = findViewById<EditText>(R.id.editNickname).text.toString()
            formData["birthday"] = findViewById<EditText>(R.id.editBirthday).text.toString()
            formData["study"] = findViewById<EditText>(R.id.editStudy).text.toString()
            formData["zodiac"] = findViewById<EditText>(R.id.editZodiac).text.toString()
            formData["hobbi"] = findViewById<EditText>(R.id.editHobbi).text.toString()
            formData["social"] = findViewById<EditText>(R.id.editSocial).text.toString()
            formData["loveBloggers"] = findViewById<EditText>(R.id.editLoveBloggers).text.toString()
            formData["loveText"] = findViewById<EditText>(R.id.editloveText).text.toString()
            formData["dream"] = findViewById<EditText>(R.id.editDream).text.toString()

            saveDataToDatabase()
            finish()
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, FormsOfFriends::class.java)
            startActivity(intent)
        }
    }

    private fun saveDataToDatabase() {
        val profileData = ProfileData(
            id = 0, // или любой другой идентификатор, если необходимо
            name = formData["name"] ?: "",
            surname = formData["surname"] ?: "",
            nickname = formData["nickname"] ?: "",
            birthday = formData["birthday"]?: "",
            study = formData["study"] ?: "",
            zodiac = formData["zodiac"] ?: "",
            hobbi = formData["hobbi"] ?: "",
            social = formData["social"] ?: "",
            loveBloggers = formData["loveBloggers"] ?: "",
            loveText = formData["loveText"] ?: "",
            dream = formData["dream"] ?: ""
        )
        dbHelper.insertData(profileData)
        Toast.makeText(this, "Анкета сохранена", Toast.LENGTH_SHORT).show()
    }
}

