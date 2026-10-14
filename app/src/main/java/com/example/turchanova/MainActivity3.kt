package com.example.turchanova

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson

class MainActivity3 : Fragment(R.layout.activity_main3) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view,  savedInstanceState)
        val textTraining = view.findViewById<TextView>(R.id.textTraining)
        val buttonClear = view.findViewById<Button>(R.id.buttonClear)
        val buttonBack = view.findViewById<Button>(R.id.buttonBack)

        val preferences = requireContext().getSharedPreferences("training_preferences", Context.MODE_PRIVATE)
        showTrainings(textTraining, preferences)

        buttonBack.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragment, MainActivity2()).commit()
        }

        buttonClear.setOnClickListener {
            preferences.edit().clear().apply()
            textTraining.text = getString(R.string.no_save)
            Toast.makeText(
                requireContext(),
                R.string.data_clear,
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
            textTraining.text = getString(R.string.no_save)
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