package com.example.labandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.ImageButton
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //backBtn
        val backButton = findViewById<ImageButton>(R.id.BackBtn)
        backButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //aboutUsBtn
        val aboutUsButton = findViewById<Button>(R.id.AboutUsBtn)
        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }

        //get and check credentials
        val loginButton = findViewById<Button>(R.id.LoginBtn)
        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.editTextTextEmailAddress).text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword).text.toString()

            // function for DB operations
            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@LoginActivity) // make DB instance
                val user = db.userDao().getUser(email, password) // query DB for credentials
                // update UI
                runOnUiThread {
                    if (user != null) {
                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, EstablishmentChooserActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid credentials!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}
