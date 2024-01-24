package com.example.mydiary

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileDetails : AppCompatActivity() {
    companion object {
        const val CHANGE_NEW_FORM_REQUEST_CODE = 1
    }

    private lateinit var dbHelper: DataBaseHelper
    private lateinit var profileId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_details)

        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            val rowsAffected = dbHelper.deleteProfile(profileId)

            if (rowsAffected > 0) {

                // Профиль успешно удален
                Toast.makeText(this, "Профиль удален", Toast.LENGTH_SHORT).show()

                // Возвращаемся к предыдущей активности
                val intent = Intent()
                intent.putExtra("profileDeleted", true)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                // Произошла ошибка при удалении профиля
                Toast.makeText(this, "Ошибка при удалении профиля", Toast.LENGTH_SHORT).show()
            }
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, LookAllForms::class.java)
            startActivity(intent)
        }
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            val intent = Intent(this, ChangeNewForm::class.java)

            // Получение данных текущего профиля
            if (profileId.isNotEmpty()) {
                val currentProfile = dbHelper.getProfileById(profileId)

                // Передача данных в Create_New_Form
                intent.putExtra("profile_id", currentProfile.id.toString())
                intent.putExtra("name", currentProfile.name)
                intent.putExtra("surname", currentProfile.surname)
                intent.putExtra("nickname", currentProfile.nickname)
                intent.putExtra("birthday", currentProfile.birthday)
                intent.putExtra("study", currentProfile.study)
                intent.putExtra("zodiac", currentProfile.zodiac)
                intent.putExtra("hobbi", currentProfile.hobbi)
                intent.putExtra("social", currentProfile.social)
                intent.putExtra("bloggers", currentProfile.loveBloggers)
                intent.putExtra("lovetext", currentProfile.loveText)
                intent.putExtra("dream", currentProfile.dream)

                startActivityForResult(intent, CHANGE_NEW_FORM_REQUEST_CODE)
            }
        }

        dbHelper = DataBaseHelper(this)

        // Получаем идентификатор профиля из Intent
        profileId = intent.getStringExtra("profile_id") ?: ""
        val selectedProfile = dbHelper.getProfileById(profileId)
        if (selectedProfile == null) {
            // Показать сообщение об ошибке
            Toast.makeText(this, "Профиль не найден", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Найдите TextView по его идентификатору в разметке и установите в него текст
        val nameTextView = findViewById<TextView>(R.id.textName)
        val surnameTextView = findViewById<TextView>(R.id.textSurname)
        val nicknameTextView = findViewById<TextView>(R.id.textNickname)
        val birthdayTextView = findViewById<TextView>(R.id.textBirthday)
        val studyTextView = findViewById<TextView>(R.id.textStudy)
        val zodiacTextView = findViewById<TextView>(R.id.textZodiac)
        val hobbiTextView = findViewById<TextView>(R.id.textHobbi)
        val socialTextView = findViewById<TextView>(R.id.textSocial)
        val bloggersTextView = findViewById<TextView>(R.id.textLoveBloggers)
        val lovetextTextView = findViewById<TextView>(R.id.textLoveText)
        val dreamTextView = findViewById<TextView>(R.id.textDream)

        // Установите текст в TextView
        nameTextView.text = selectedProfile.name
        surnameTextView.text = selectedProfile.surname
        nicknameTextView.text = selectedProfile.nickname
        birthdayTextView.text = selectedProfile.birthday
        studyTextView.text = selectedProfile.study
        zodiacTextView.text = selectedProfile.zodiac
        hobbiTextView.text = selectedProfile.hobbi
        socialTextView.text = selectedProfile.social
        bloggersTextView.text = selectedProfile.loveBloggers
        lovetextTextView.text = selectedProfile.loveText
        dreamTextView.text = selectedProfile.dream
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CHANGE_NEW_FORM_REQUEST_CODE && resultCode == RESULT_OK) {
            // Обновление отображаемых данных в ProfileDetails
            updateDisplayedData()
        }
    }

    private fun updateDisplayedData() {
        // Код для обновления отображаемых данных
        // Например, повторное получение данных из базы и обновление TextView
        val selectedProfile = dbHelper.getProfileById(profileId)
        if (selectedProfile != null) {

            // Обновление данных в TextView
            val nameTextView = findViewById<TextView>(R.id.textName)
            nameTextView.text = selectedProfile.name
            val surnameTextView = findViewById<TextView>(R.id.textSurname)
            surnameTextView.text = selectedProfile.surname
            val nicknameTextView = findViewById<TextView>(R.id.textNickname)
            nicknameTextView.text = selectedProfile.nickname
            val birthdayTextView = findViewById<TextView>(R.id.textBirthday)
            birthdayTextView.text = selectedProfile.birthday
            val studyTextView = findViewById<TextView>(R.id.textStudy)
            studyTextView.text = selectedProfile.study
            val zodiacTextView = findViewById<TextView>(R.id.textZodiac)
            zodiacTextView.text = selectedProfile.zodiac
            val hobbiTextView = findViewById<TextView>(R.id.textHobbi)
            hobbiTextView.text = selectedProfile.hobbi
            val socialTextView = findViewById<TextView>(R.id.textSocial)
            socialTextView.text = selectedProfile.social
            val loveBloggersTextView = findViewById<TextView>(R.id.textLoveBloggers)
            loveBloggersTextView.text = selectedProfile.loveBloggers
            val loveTextTextView = findViewById<TextView>(R.id.textLoveText)
            loveTextTextView.text = selectedProfile.loveText
            val dreamTextView = findViewById<TextView>(R.id.textDream)
            dreamTextView.text = selectedProfile.dream
        }
    }
}