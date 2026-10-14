package com.example.turchanova

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import android.widget.Toast
import androidx.fragment.app.Fragment

class MainActivity2 :  Fragment(R.layout.activity_main2){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view,  savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val spinnerFrom = view.findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = view.findViewById<Spinner>(R.id.spinnerTo)
        val spinnerDay = view.findViewById<Spinner>(R.id.spinnerDay)
        val opisan = view.findViewById<EditText>(R.id.opisan)
        val buttonSave = view.findViewById<Button>(R.id.buttonSave)
        val buttonNext = view.findViewById<Button>(R.id.buttonNext)
        val trainingAdapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.training_types,
            android.R.layout.simple_spinner_item
        )
        trainingAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = trainingAdapter

        val fromAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.time_from,
            android.R.layout.simple_spinner_item
        )
        fromAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerFrom.adapter = fromAdapter

        val toAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.time_to,
            android.R.layout.simple_spinner_item
        )
        toAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerTo.adapter = toAdapter

        val dayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
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
            val preferences = requireContext().getSharedPreferences("training_preferences", Context.MODE_PRIVATE)
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
                    requireContext(),
                    R.string.one,
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
            Toast.makeText(requireContext(), R.string.two, Toast.LENGTH_LONG).show()
        }

        buttonNext.setOnClickListener {

            parentFragmentManager.beginTransaction().replace(R.id.fragment, MainActivity3()).commit()

        }
    }
}