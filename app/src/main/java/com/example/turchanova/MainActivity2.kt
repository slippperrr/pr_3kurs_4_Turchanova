package com.example.turchanova

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val spinnerDay = findViewById<Spinner>(R.id.spinnerDay)
        val opisan = findViewById<EditText>(R.id.opisan)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        val trainingAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.training_types,
            android.R.layout.simple_spinner_item
        )
        trainingAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = trainingAdapter

        val fromAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.time_from,
            android.R.layout.simple_spinner_item
        )
        fromAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerFrom.adapter = fromAdapter

        val toAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.time_to,
            android.R.layout.simple_spinner_item
        )
        toAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerTo.adapter = toAdapter

        val dayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.days,
            android.R.layout.simple_spinner_item
        )
        dayAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerDay.adapter = dayAdapter

        buttonSave.setOnClickListener {

            val training = Training(
                activity = spinner.selectedItem.toString(),
                from = spinnerFrom.selectedItem.toString(),
                to = spinnerTo.selectedItem.toString(),
                day = spinnerDay.selectedItem.toString(),
                opis = opisan.text.toString()
            )

            val gson = Gson()
            val preferences = getSharedPreferences("training_preferences", MODE_PRIVATE)
            val json = preferences.getString("training_list", null)
            val list: MutableList<Training> = if (json != null) {
                gson.fromJson(json, Array<Training>::class.java).toMutableList()
            }
            else {
                mutableListOf()
            }
            val alreadyExists = list.any { existing ->
                existing.activity == training.activity &&
                        existing.from == training.from &&
                        existing.to == training.to &&
                        existing.day == training.day &&
                        existing.opis == training.opis
            }

            if (alreadyExists) {
                Toast.makeText(
                    this,
                    "Такая запись уже существует",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            list.add(0, training)

            while (list.size > 3) {
                list.removeAt(list.size - 1)
            }
            val newJson = gson.toJson(list)
            preferences.edit()
                .putString("training_list", newJson)
                .apply()

            //сохранение
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show()
        }

        buttonNext.setOnClickListener {

            val intent = Intent(
                this,
                MainActivity3::class.java
            )
            startActivity(intent)
        }
    }
}