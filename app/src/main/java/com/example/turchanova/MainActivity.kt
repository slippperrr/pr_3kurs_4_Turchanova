package com.example.turchanova

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDnevnik = findViewById<Button>(R.id.buttonDnevnik)
        val editPas = findViewById<EditText>(R.id.editPas)
        val editLog = findViewById<EditText>(R.id.editLog)
        val preferences = getSharedPreferences("user_preferences",MODE_PRIVATE)



        buttonDnevnik.setOnClickListener {

            val log = editLog.text.toString()
            val pas = editPas.text.toString()

            if (log.isEmpty()) {
                editLog.error = "Введите логин"
                return@setOnClickListener
            }

            if (pas.isEmpty()) {
                editPas.error = "Введите пароль"
                return@setOnClickListener
            }

            preferences.edit()
                .putString("login", log)
                .putString("password", pas)
                .apply()
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}