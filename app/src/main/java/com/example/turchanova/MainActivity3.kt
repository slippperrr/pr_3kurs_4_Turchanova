package com.example.turchanova

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val textTraining = findViewById<TextView>(R.id.textTraining)
        val buttonClear = findViewById<Button>(R.id.buttonClear)
        val buttonBack = findViewById<Button>(R.id.buttonBack)

        val preferences = getSharedPreferences("training_preferences", MODE_PRIVATE)
        showTrainings(textTraining, preferences)
        buttonBack.setOnClickListener {
            finish()
        }


        buttonClear.setOnClickListener {
            preferences.edit().clear().apply()
            textTraining.text = "Нет сохранённых тренировок"
            Toast.makeText(
                this,
                "Данные очищены",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showTrainings(textTraining: TextView, preferences: SharedPreferences)
    {
        val json = preferences.getString("training_list", null)
        val gson = Gson()
        val list = try {
            gson.fromJson(json, Array<Training>::class.java)
        }
        catch (e: Exception) {
            null
        }

        if (list.isNullOrEmpty()) {
            textTraining.text = "Нет сохранённых тренировок"
            return
        }

        val sb = StringBuilder()
        list.forEachIndexed { index, training ->
            sb.append("Занятие ${index + 1}:\n")
            sb.append("Вид: ${training.activity}\n")
            sb.append("Время: ${training.from} — ${training.to}\n")
            sb.append("День: ${training.day}\n")
            sb.append("Описание: ${training.opis}\n")
            sb.append("\n")
        }

        textTraining.text = sb.toString()
    }
}