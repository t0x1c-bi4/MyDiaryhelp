package com.example.mydiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChangeNewForm : AppCompatActivity() {
    private lateinit var dbHelper: DataBaseHelper
    private val formData = HashMap<String, String>() // Для хранения данных из полей

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.f_change_new_form)

        dbHelper = DataBaseHelper(this)

        // Получение данных из Intent
        val profileId = intent.getStringExtra("profile_id")
        val name = intent.getStringExtra("name")
        val surname = intent.getStringExtra("surname")
        val nickname = intent.getStringExtra("nickname")
        val birthday = intent.getStringExtra("birthday")
        val study = intent.getStringExtra("study")
        val zodiac = intent.getStringExtra("zodiac")
        val hobbi = intent.getStringExtra("hobbi")
        val social = intent.getStringExtra("social")
        val loveBloggers = intent.getStringExtra("bloggers")
        val loveText = intent.getStringExtra("lovetext")
        val dream = intent.getStringExtra("dream")

        // Заполнение элементов интерфейса данными
        val nameEditText = findViewById<EditText>(R.id.editName)
        val surnameEditText = findViewById<EditText>(R.id.editSurname)
        val nicknameEditText = findViewById<EditText>(R.id.editNickname)
        val birthdayEditText = findViewById<EditText>(R.id.editBirthday)
        val studyEditText = findViewById<EditText>(R.id.editStudy)
        val zodiacEditText = findViewById<EditText>(R.id.editZodiac)
        val hobbiEditText = findViewById<EditText>(R.id.editHobbi)
        val socialEditText = findViewById<EditText>(R.id.editSocial)
        val loveBloggersEditText = findViewById<EditText>(R.id.editLoveBloggers)
        val loveTextEditText = findViewById<EditText>(R.id.editloveText)
        val dreamEditText = findViewById<EditText>(R.id.editDream)

        // Установка текста в соответствующие элементы
        nameEditText.setText(name)
        surnameEditText.setText(surname)
        nicknameEditText.setText(nickname)
        birthdayEditText.setText(birthday)
        studyEditText.setText(study)
        zodiacEditText.setText(zodiac)
        hobbiEditText.setText(hobbi)
        socialEditText.setText(social)
        loveBloggersEditText.setText(loveBloggers)
        loveTextEditText.setText(loveText)
        dreamEditText.setText(dream)

        // Далее обработка сохранения изменений

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {

            formData["name"] = nameEditText.text.toString()
            formData["surname"] = surnameEditText.text.toString()
            formData["nickname"] = nicknameEditText.text.toString()
            formData["birthday"] = birthdayEditText.text.toString()
            formData["study"] = studyEditText.text.toString()
            formData["zodiac"] = zodiacEditText.text.toString()
            formData["hobbi"] = hobbiEditText.text.toString()
            formData["social"] = socialEditText.text.toString()
            formData["bloggers"] = loveBloggersEditText.text.toString()
            formData["lovetext"] = loveTextEditText.text.toString()
            formData["dream"] = dreamEditText.text.toString()

            // Сохранение изменённых данных в базе данных
            updateDataInDatabase(profileId)
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun updateDataInDatabase(profileId: String?) {
        // Проверка наличия идентификатора профиля
        if (profileId.isNullOrEmpty()) {
            Toast.makeText(this, "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show()
            return
        }

        // Обновление данных в базе данных
        val profileData = ProfileData(
            id = profileId.toInt(), // Преобразование идентификатора в Int
            name = formData["name"] ?: "",
            surname = formData["surname"] ?: "",
            nickname = formData["nickname"] ?: "",
            birthday = formData["birthday"] ?: "",
            study = formData["study"] ?: "",
            zodiac = formData["zodiac"] ?: "",
            hobbi = formData["hobbi"] ?: "",
            social = formData["social"] ?: "",
            loveBloggers = formData["bloggers"] ?: "",
            loveText = formData["lovetext"] ?: "",
            dream = formData["dream"] ?: ""
        )

        // Вызов метода для обновления данных в базе
        dbHelper.updateData(profileData)

        // Установка результата
        setResult(RESULT_OK)

        // Оповещение пользователя об успешном сохранении
        Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
    }
}