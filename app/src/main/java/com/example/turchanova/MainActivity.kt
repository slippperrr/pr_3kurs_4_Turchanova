package com.example.turchanova

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class MainActivity : Fragment(R.layout.activity_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonDnevnik = view.findViewById<Button>(R.id.buttonDnevnik)
        val editPas = view.findViewById<EditText>(R.id.editPas)
        val editLog = view.findViewById<EditText>(R.id.editLog)
        val preferences = requireContext().getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

        val savedLogin = preferences.getString("login", null)
        val savedPassword = preferences.getString("password", null)

        if (!savedLogin.isNullOrEmpty()) {
            editLog.setText(savedLogin)
        }
        if (!savedPassword.isNullOrEmpty()) {
            editPas.setText(savedPassword)
        }

        buttonDnevnik.setOnClickListener {

            val log = editLog.text.toString()
            val pas = editPas.text.toString()

            if (log.isEmpty()) {
                editLog.error = getString(R.string.log)
                return@setOnClickListener
            }

            if (pas.isEmpty()) {
                editPas.error = getString(R.string.pass)
                return@setOnClickListener
            }

            preferences.edit()
                .putString("login", log)
                .putString("password", pas)
                .apply()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment, MainActivity2())
                .commit()
        }
    }
}