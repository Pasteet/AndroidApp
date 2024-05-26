package com.example.labandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast
import android.content.Intent

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val aboutUsButton = findViewById<Button>(R.id.AboutUsBtn)
        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.LoginBtn)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val registerButton = findViewById<Button>(R.id.RegisterBtn)
        registerButton.setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextName).text.toString()
            val surname = findViewById<EditText>(R.id.editTextSurname).text.toString()
            val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

            val user = User(name = name, surname = surname, email = email, password = password)

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@RegisterActivity)
                db.userDao().insert(user)
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "User registered!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}