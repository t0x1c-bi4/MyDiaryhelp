package com.example.mydiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import android.os.Handler

class StartLoadingScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 3000 // 3 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_loading_screen)

        // Используем Handler для задержки перехода к основной активности
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрываем StartLoadingScreen после перехода
        }, SPLASH_TIME_OUT)
    }
}
